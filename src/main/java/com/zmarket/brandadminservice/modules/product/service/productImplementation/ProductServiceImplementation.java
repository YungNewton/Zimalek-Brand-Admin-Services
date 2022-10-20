package com.zmarket.brandadminservice.modules.product.service.productImplementation;

import com.zmarket.brandadminservice.exceptions.BadRequestException;
import com.zmarket.brandadminservice.exceptions.ForbiddenException;
import com.zmarket.brandadminservice.exceptions.NoContentException;
import com.zmarket.brandadminservice.exceptions.NotFoundException;
import com.zmarket.brandadminservice.modules.brand.models.Brand;
import com.zmarket.brandadminservice.modules.brand.repositories.BrandRepository;
import com.zmarket.brandadminservice.modules.colour.dto.ColourDto;
import com.zmarket.brandadminservice.modules.colour.model.Colour;
import com.zmarket.brandadminservice.modules.colour.repository.ColourRepository;
import com.zmarket.brandadminservice.modules.images.model.Image;
import com.zmarket.brandadminservice.modules.images.repository.ImageRepository;
import com.zmarket.brandadminservice.modules.product.dto.ProductDto;
import com.zmarket.brandadminservice.modules.product.model.Product;
import com.zmarket.brandadminservice.modules.product.repositories.ProductRepository;
import com.zmarket.brandadminservice.modules.product.service.ProductServices;
import com.zmarket.brandadminservice.modules.security.jwt.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductServices {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CurrentUser currentUser;
    private final ImageRepository imageRepository;

    private final ColourRepository colourRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Product createNewProduct(ProductDto request) {
        Optional<Product> optionalProduct = productRepository.findByProductName(request.getProductName());
        if (optionalProduct.isPresent()){
            throw new BadRequestException("Category with name already exists");
        }
        if(Objects.isNull(request.getImageUrls()) || request.getImageUrls().isEmpty()){
            throw new BadRequestException("Product image is required");
        }
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setCreatedAt(new Date());
        product.setNew(request.isNew());
        product.setProductId(request.getProductId());
        product.setQuantity(request.getQuantity());
        product.setUserId(currentUser.getId());
        product.setUnitPrice(request.getUnitPrice());
        Set<Image> images = saveImages(request.getImageUrls());
        Set<Colour> colours = saveColours(request.getColours());
        product = productRepository.save(product);
        return product;
    }
    @Override
    public Product getById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            throw new NotFoundException("Category with id not found");
        }
        return optionalProduct.get();
    }

    private List<Predicate> getPredicates(LocalDateTime start, LocalDateTime end, String name, String colour, String category, BigDecimal price, CriteriaBuilder qb, Root<Product> root, CriteriaQuery<Product> cq) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(start)) {
            predicates.add(qb.greaterThanOrEqualTo(root.get("createdAt"), start));
        }

        if (Objects.nonNull(end)) {
            predicates.add(qb.lessThan(root.get("createdAt"), end));
        }

        if (Objects.nonNull(name)) {
            predicates.add(qb.equal(root.get("productName"), name));
        }

        if (Objects.nonNull(category)) {
            predicates.add(qb.equal(root.get("category"), category));
        }

        if (Objects.nonNull(price)) {
            predicates.add(qb.equal(root.get("price"), price));
        }

        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<Product> subqueryStudent = subquery.from(Product.class);
        Join<Colour, Product> subqueryCourse = subqueryStudent.join("colours");

        subquery.select(subqueryStudent.get("id")).where(qb.equal(subqueryCourse.get("name"), colour));

        predicates.add(qb.in(root.get("id")).value(subquery));

        return predicates;
    }

    @Override
    public Page<Product> getAll(LocalDate startDate, LocalDate endDate, String name, String color, String category, BigDecimal price, Pageable pageable) {

        LocalDateTime start = Objects.nonNull(startDate) ? LocalDateTime.of(startDate, LocalTime.MIN) : null;
        LocalDateTime end = Objects.nonNull(startDate) ? LocalDateTime.of(endDate, LocalTime.MAX) : null;

        if (Objects.isNull(start) && Objects.isNull(name) && Objects.isNull(color) && Objects.isNull(category) && Objects.isNull(price)) {
            Page<Product> products = productRepository.findAll(pageable);
            if(products.isEmpty()){
                throw new NoContentException();
            }

            return products;
        }
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Product> cq = qb.createQuery(Product.class);

        Root<Product> root = cq.from(Product.class);

        List<Predicate> predicates = getPredicates(start, end, name, color, category, price, qb, root, cq);

        cq.select(root).where(predicates.toArray(new Predicate[]{}));

        List<Product> res = entityManager.createQuery(cq).getResultList();

        if (res.isEmpty()) {
            throw new NoContentException();
        }

        return new PageImpl<>(res, pageable, res.size());
    }

    @Override
    public Product update(Long id, ProductDto dto) {

        Optional<Product> optionalProduct = productRepository.findByIdAndUserId(id, currentUser.getId());

        if (optionalProduct.isEmpty()){
            throw new ForbiddenException("you are not authorized to view this resource");
        }

        Product product = optionalProduct.get();
        product.setNew(dto.isNew());
        product.setProductId(dto.getProductId());
        product.setQuantity(dto.getQuantity());
        product.setUnitPrice(dto.getUnitPrice());
        optionalProduct.get().setUpdatedAt(new Date());
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        Set<Image> images = saveImages(dto.getImageUrls());
        Set<Colour> colours = saveColours(dto.getColours());
        product = productRepository.save(product);
        return product;
    }

    @Override
    public void delete(Long id) {

        Optional<Product> optionalProduct = productRepository.findByIdAndUserId(id, currentUser.getId());

        if (optionalProduct.isEmpty()){
            throw new ForbiddenException("you are authorized to view this resource");
        }

        productRepository.delete(optionalProduct.get());
        log.info("category with id deleted");
    }
    private Set<Colour> saveColours(Set<ColourDto> dto) {
        if (Objects.isNull(dto) || dto.isEmpty()) {
            return new HashSet<>();
        }
        Set<Colour> colors = new HashSet<>();
        dto.forEach(m -> colors.add(colourRepository.save(new Colour(m.getName(), m.getCode(), new Date()))));
        return colors;
    }

    private Set<Image> saveImages(Set<String> imageUrls) {
        Set<Image> images = new HashSet<>();
        imageUrls.forEach(m -> images.add(imageRepository.save(new Image(m,new Date()))));
        return images;
    }

    @Override
    public Page<Product> getProductByBrandId(Long brandId, LocalDate startDate, LocalDate endDate, String name, String color, String category, BigDecimal price, Pageable pageable) {
        Optional<Brand> brand = brandRepository.findById(brandId);
        if(brand.isEmpty()){
            throw new NotFoundException("Brand does not exist");
        }

        LocalDateTime start = Objects.nonNull(startDate) ? LocalDateTime.of(startDate, LocalTime.MIN) : null;
        LocalDateTime end = Objects.nonNull(startDate) ? LocalDateTime.of(endDate, LocalTime.MAX) : null;

        if (Objects.isNull(start) && Objects.isNull(end) && Objects.isNull(name) && Objects.isNull(color) && Objects.isNull(category) && Objects.isNull(price)) {
            Page<Product> products = productRepository.findByBrand(brand.get(), pageable);
            if(products.isEmpty()){
                throw new NoContentException();
            }

            return products;
        }

        CriteriaBuilder qb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Product> cq = qb.createQuery(Product.class);

        Root<Product> root = cq.from(Product.class);

        List<Predicate> predicates = getPredicates(start, end, name, color, category, price, qb, root, cq);

        predicates.add(qb.equal(root.get("brand"), brand.get()));

        cq.select(root).where(predicates.toArray(new Predicate[]{}));

        List<Product> res = entityManager.createQuery(cq).getResultList();

        if (res.isEmpty()) {
            throw new NoContentException();
        }

        return new PageImpl<>(res, pageable, res.size());
    }
}
