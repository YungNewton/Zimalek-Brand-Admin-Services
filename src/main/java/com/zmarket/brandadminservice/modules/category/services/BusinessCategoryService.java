package com.zmarket.brandadminservice.modules.category.services;

import com.zmarket.brandadminservice.modules.category.dtos.BusinessCategoryDto;
import com.zmarket.brandadminservice.modules.category.models.BusinessCategory;

import java.util.List;

public interface BusinessCategoryService {
    BusinessCategory createBusinessCategory(BusinessCategoryDto request);
    BusinessCategory getById(Long id);
    List<BusinessCategory> getAll();
    BusinessCategory update(Long id,BusinessCategoryDto dto);
    void delete(Long id);

}
