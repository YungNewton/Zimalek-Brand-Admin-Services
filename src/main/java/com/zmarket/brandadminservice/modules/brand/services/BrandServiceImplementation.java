package com.zmarket.brandadminservice.modules.brand.services;

import com.zmarket.brandadminservice.exceptions.BadRequestException;
import com.zmarket.brandadminservice.exceptions.ForbiddenException;
import com.zmarket.brandadminservice.exceptions.NotFoundException;
import com.zmarket.brandadminservice.modules.brand.dtos.BrandDto;
import com.zmarket.brandadminservice.modules.brand.dtos.BusinessVerificationResponse;
import com.zmarket.brandadminservice.modules.brand.enums.BusinessType;
import com.zmarket.brandadminservice.modules.brand.models.Brand;
import com.zmarket.brandadminservice.modules.brand.repositories.BrandRepository;
import com.zmarket.brandadminservice.modules.category.models.BusinessCategory;
import com.zmarket.brandadminservice.modules.category.repositories.BusinessCategoryRepository;
import com.zmarket.brandadminservice.modules.security.jwt.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j

public class BrandServiceImplementation implements BrandService{
    private final BrandRepository brandRepository;
    private final CurrentUser currentUser;

    private final BusinessVerificationFactory businessVerificationFactory;
    private final BusinessCategoryRepository businessCategoryRepository;

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

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
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

        //todo change domainUrl to handle

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
