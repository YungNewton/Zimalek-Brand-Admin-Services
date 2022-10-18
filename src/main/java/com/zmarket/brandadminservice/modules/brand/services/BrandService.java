package com.zmarket.brandadminservice.modules.brand.services;

import com.zmarket.brandadminservice.modules.brand.dtos.BrandDto;
import com.zmarket.brandadminservice.modules.brand.dtos.BusinessVerificationResponse;
import com.zmarket.brandadminservice.modules.brand.models.Brand;
import com.zmarket.brandadminservice.modules.product.dto.ProductDto;
import com.zmarket.brandadminservice.modules.product.model.Product;

import java.util.List;

public interface BrandService {
    BusinessVerificationResponse verifyBusiness(String number, String name, String type);
    Brand createBrand(BrandDto request);
    Brand getById(Long id);
    List<Brand> getAll();
    Brand update(Long id, BrandDto dto);
}
