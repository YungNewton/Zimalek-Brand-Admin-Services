package com.zmarket.brandadminservice.modules.product.service.productImplementation;

import com.zmarket.brandadminservice.exceptions.BadRequestException;
import com.zmarket.brandadminservice.exceptions.ForbiddenException;
import com.zmarket.brandadminservice.exceptions.NotFoundException;
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
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductServices {

    private final ProductRepository productRepository;
    private final CurrentUser currentUser;
    private final ImageRepository imageRepository;

    private final ColourRepository colourRepository;


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

    @Override
    public List<Product> getAll() {
        return  productRepository.findAll();
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
}
