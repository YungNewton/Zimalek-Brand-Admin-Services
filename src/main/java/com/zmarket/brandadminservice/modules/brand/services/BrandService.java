package com.zmarket.brandadminservice.modules.brand.services;

import com.zmarket.brandadminservice.modules.brand.dtos.BrandDto;
import com.zmarket.brandadminservice.modules.brand.dtos.BusinessVerificationResponse;
import com.zmarket.brandadminservice.modules.brand.dtos.UpdateBrandDto;
import com.zmarket.brandadminservice.modules.brand.models.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface BrandService {
    BusinessVerificationResponse verifyBusiness(String number, String name, String type);
    Brand createBrand(BrandDto request);
    Brand getById(Long id);
    Page<Brand> getAll(LocalDate start, LocalDate end, String category, Pageable pageable);
    Brand update(Long id, UpdateBrandDto dto);
    List<Brand> getAllAdminBrand();
    Brand getBrandByHandle(String handle);
}
