package com.zmarket.brandadminservice.modules.product.service;

import com.zmarket.brandadminservice.modules.product.dto.ProductDto;
import com.zmarket.brandadminservice.modules.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ProductServices {
    Product createNewProduct(ProductDto request);
    Product getById(Long id);
    Page<Product> getAll(LocalDate start, LocalDate end, String name, String color, String category, BigDecimal price, Pageable pageable);
    Page<Product> getProductByBrandId(Long brandId, LocalDate startDate, LocalDate endDate, String name, String color, String category, BigDecimal price, Pageable pageable);
    Product update(Long id, ProductDto dto);
    void delete(Long id);
}
