package com.zmarket.brandadminservice.modules.brand.services;

import com.zmarket.brandadminservice.exceptions.BadRequestException;
import com.zmarket.brandadminservice.exceptions.ForbiddenException;
import com.zmarket.brandadminservice.exceptions.NoContentException;
import com.zmarket.brandadminservice.exceptions.NotFoundException;
import com.zmarket.brandadminservice.modules.brand.dtos.BrandDto;
import com.zmarket.brandadminservice.modules.brand.dtos.BusinessVerificationResponse;
import com.zmarket.brandadminservice.modules.brand.enums.BusinessType;
import com.zmarket.brandadminservice.modules.brand.models.Brand;
import com.zmarket.brandadminservice.modules.brand.repositories.BrandRepository;
import com.zmarket.brandadminservice.modules.category.models.BusinessCategory;
import com.zmarket.brandadminservice.modules.category.repositories.BusinessCategoryRepository;
import com.zmarket.brandadminservice.modules.colour.model.Colour;
import com.zmarket.brandadminservice.modules.product.model.Product;
import com.zmarket.brandadminservice.modules.security.jwt.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j

public class BrandServiceImplementation implements BrandService{
    private final BrandRepository brandRepository;
    private final CurrentUser currentUser;

    private final BusinessVerificationFactory businessVerificationFactory;
    private final BusinessCategoryRepository businessCategoryRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Brand createBrand(BrandDto request) {
        Optional<Brand> optionalBrand = brandRepository.findByName(request.getName());
        if (optionalBrand.isPresent()){
            throw new BadRequestException("Brand with name already exists");
        }

        Optional<BusinessCategory> optionalBusinessCategory = businessCategoryRepository.findById(request.getCategoryId());
        if (optionalBusinessCategory.isEmpty()){
            throw new NotFoundException("Category with Id doesn't exists");
        }

        BusinessVerificationResponse verificationResponse;
        verificationResponse = verifyBusiness(request.getBusinessNumber(), null, request.getName());

        Brand brand = new Brand();
        brand.setName(verificationResponse.getName());
        brand.setCategory(optionalBusinessCategory.get());
        brand.setBusinessNumber(request.getBusinessNumber());
        brand.setBusinessType(request.getBusinessType());
        brand.setHandle(request.getHandle());
        brand.setAddress(request.getAddress());
        brand.setFacebookUrl(request.getFacebookUrl());
        brand.setTwitterUrl(request.getTwitterUrl());
        brand.setInstagramUrl(request.getInstagramUrl());
        brand.setLogo(request.getLogo());
        brand.setUserId(currentUser.getId());
        brand.setCreatedAt(new Date());
        brand = brandRepository.save(brand);
        return brand;
    }

    @Override
    public Brand getById(Long id) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isEmpty()){
            throw new NotFoundException("Brand with id not found");
        }
        return optionalBrand.get();
    }

    @Override
    public BusinessVerificationResponse verifyBusiness(String number, String type ,String name) {
        BusinessVerificationService verificationService = businessVerificationFactory.resolve("dojah");
        BusinessVerificationResponse verificationResponse = verificationService.verify(number, BusinessType.valueOf(type), name);
        return verificationResponse;
    }

    private List<Predicate> getPredicates(LocalDateTime start, LocalDateTime end, String name, String colour, String category, BigDecimal price, CriteriaBuilder qb, Root<Brand> root, CriteriaQuery<Brand> cq) {
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
    public Page<Brand> getAll(LocalDate startDate, LocalDate endDate, String name, String color, String category, BigDecimal price, Pageable pageable) {
        LocalDateTime start = Objects.nonNull(startDate) ? LocalDateTime.of(startDate, LocalTime.MIN) : null;
        LocalDateTime end = Objects.nonNull(startDate) ? LocalDateTime.of(endDate, LocalTime.MAX) : null;

        if (Objects.isNull(start) && Objects.isNull(name) && Objects.isNull(color) && Objects.isNull(category) && Objects.isNull(price)) {
            Page<Brand> brand = brandRepository.findAll(pageable);
            if(brand.isEmpty()){
                throw new NoContentException();
            }

            return brand;
        }
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Brand> cq = qb.createQuery(Brand.class);

        Root<Brand> root = cq.from(Brand.class);

        List<Predicate> predicates = getPredicates(start, end, name, color, category, price, qb, root, cq);

        cq.select(root).where(predicates.toArray(new Predicate[]{}));

        List<Brand> res = entityManager.createQuery(cq).getResultList();

        if (res.isEmpty()) {
            throw new NoContentException();
        }

        return new PageImpl<>(res, pageable, res.size());
    }

    @Override
    public Brand update(Long id, BrandDto dto) {

        Optional<Brand> optionalBrand = brandRepository.findByIdAndUserId(id, currentUser.getId());

        Optional<BusinessCategory> optionalBusinessCategory = businessCategoryRepository.findById(dto.getCategoryId());
        if (optionalBrand.isEmpty()){
            throw new ForbiddenException("you are authorized to view this resource");
        }
        if (optionalBusinessCategory.isEmpty()){
            throw new NotFoundException("Category with Id doesn't exists");
        }

        Brand brand = optionalBrand.get();
        brand.setCategory(optionalBusinessCategory.get());
        brand.setBusinessType(dto.getBusinessType());
        brand.setHandle(dto.getHandle());
        brand.setAddress(dto.getAddress());
        brand.setFacebookUrl(dto.getFacebookUrl());
        brand.setTwitterUrl(dto.getTwitterUrl());
        brand.setInstagramUrl(dto.getInstagramUrl());
        brand.setLogo(dto.getLogo());
        brand.setUpdatedAt(new Date());
        return brand;
    }
}
