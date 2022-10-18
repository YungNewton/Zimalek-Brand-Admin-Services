package com.zmarket.brandadminservice.modules.product.service;

import com.zmarket.brandadminservice.modules.product.dto.ProductDto;
import com.zmarket.brandadminservice.modules.product.model.Product;

import java.util.List;

public interface ProductServices {
    Product createNewProduct(ProductDto request);
    Product getById(Long id);
    List<Product> getAll();
    Product update(Long id, ProductDto dto);
    void delete(Long id);
}
