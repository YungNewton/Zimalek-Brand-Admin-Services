package com.zmarket.brandadminservice.modules.brand.controllers;


import com.zmarket.brandadminservice.annotations.WrapApiResponse;
import com.zmarket.brandadminservice.modules.brand.dtos.BrandDto;
import com.zmarket.brandadminservice.modules.brand.dtos.BusinessVerificationResponse;
import com.zmarket.brandadminservice.modules.brand.dtos.UpdateBrandDto;
import com.zmarket.brandadminservice.modules.brand.models.Brand;
import com.zmarket.brandadminservice.modules.brand.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/brands")
@WrapApiResponse
@RequiredArgsConstructor
public class BrandAdminController {
    private final BrandService brandService;

    @PreAuthorize("hasRole('ROLE_BRAND_SELLER')")
    @PostMapping
    public Brand create(@RequestBody @Valid BrandDto brandDto ){
        return brandService.createBrand(brandDto);
    }

    @PreAuthorize("hasRole('ROLE_BRAND_SELLER')")
    @PostMapping("/verify")
    public BusinessVerificationResponse verify(@RequestParam String name, @RequestParam String number, @RequestParam(required = false) String type){
        return brandService.verifyBusiness(name, number, type);
    }


    @PreAuthorize("hasRole('ROLE_BRAND_SELLER')")
    @GetMapping
    public List<Brand> getAllAdminBrand(){
        return brandService.getAllAdminBrand();
    }

    @PreAuthorize("hasRole('ROLE_BRAND_SELLER')")
    @PutMapping("/{id}")
    public Brand update(@PathVariable("id") Long id,@Valid @RequestBody UpdateBrandDto brandDto) {
        return brandService.update(id,brandDto);
    }
}
