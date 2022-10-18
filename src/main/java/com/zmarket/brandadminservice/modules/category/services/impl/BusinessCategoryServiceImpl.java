package com.zmarket.brandadminservice.modules.category.services.impl;

import com.zmarket.brandadminservice.exceptions.BadRequestException;
import com.zmarket.brandadminservice.exceptions.NotFoundException;
import com.zmarket.brandadminservice.modules.category.dtos.BusinessCategoryDto;
import com.zmarket.brandadminservice.modules.category.models.BusinessCategory;
import com.zmarket.brandadminservice.modules.category.repositories.BusinessCategoryRepository;
import com.zmarket.brandadminservice.modules.category.services.BusinessCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BusinessCategoryServiceImpl implements BusinessCategoryService{

    private final BusinessCategoryRepository businessCategoryRepository;

    @Override
    public BusinessCategory createBusinessCategory(BusinessCategoryDto request) {
        Optional<BusinessCategory> optionalBusiness = businessCategoryRepository.findByName(request.getName());
        if (optionalBusiness.isPresent()){
            throw new BadRequestException("Category with name already exists");
        }
        BusinessCategory businessCategory = new BusinessCategory();
        businessCategory.setName(request.getName());
        businessCategory.setDescription(request.getDescription());
        businessCategory.setCreatedAt(new Date());
        businessCategory = businessCategoryRepository.save(businessCategory);
        return businessCategory;
    }

    @Override
    public BusinessCategory getById(Long id) {
        Optional<BusinessCategory> optionalBusiness = businessCategoryRepository.findById(id);
        if (optionalBusiness.isEmpty()){
            throw new NotFoundException("Category with id not found");
        }
        return optionalBusiness.get();
    }

    @Override
    public List<BusinessCategory> getAll() {
        return businessCategoryRepository.findAll();
    }

    @Override
    public BusinessCategory update(Long id,BusinessCategoryDto dto) {
        Optional<BusinessCategory> optionalBusiness = businessCategoryRepository.findById(id);
        if (optionalBusiness.isEmpty()){
            throw new BadRequestException("Category with id doesn't exists");
        }
        BusinessCategory category = optionalBusiness.get();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setUpdatedAt(new Date());
        category = businessCategoryRepository.save(category);
        return category;
    }

    @Override
    public void delete(Long id) {
        Optional<BusinessCategory> optionalBusiness = businessCategoryRepository.findById(id);
        if (optionalBusiness.isEmpty()){
            throw new NotFoundException("Category with id not found");
        }
        businessCategoryRepository.delete(optionalBusiness.get());
        log.info("category with id deleted");
    }
}
