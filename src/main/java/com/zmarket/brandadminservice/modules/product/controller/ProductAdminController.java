package com.zmarket.brandadminservice.modules.product.controller;

import com.zmarket.brandadminservice.annotations.WrapApiResponse;
import com.zmarket.brandadminservice.modules.product.dto.ProductDto;
import com.zmarket.brandadminservice.modules.product.model.Product;
import com.zmarket.brandadminservice.modules.product.service.ProductServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/products")
@WrapApiResponse
@RequiredArgsConstructor
public class ProductAdminController {
    private final ProductServices productServices;

    @PreAuthorize("hasRole('ROLE_BRAND_SELLER')")
    @PostMapping
    public Product create(@RequestBody @Valid ProductDto productDto, @RequestHeader(value = "brand-id") Long brandId){
        return productServices.createNewProduct(productDto, brandId);
    }

    @PreAuthorize("hasRole('ROLE_BRAND_SELLER')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id, @RequestHeader(value = "brand-id") Long brandId){
        productServices.delete(id, brandId);
    }

    @PreAuthorize("hasRole('ROLE_BRAND_SELLER')")
    @PutMapping("/{id}")
    public Product update(@PathVariable("id") Long id,@Valid @RequestBody ProductDto productDto, @RequestHeader(value = "brand-id") Long brandId) {
        return productServices.update(id,productDto, brandId);
    }
}
