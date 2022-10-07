package com.zmarket.brandadminservice.modules.category.controllers;

import com.zmarket.brandadminservice.annotations.WrapApiResponse;
import com.zmarket.brandadminservice.modules.category.dtos.BusinessCategoryDto;
import com.zmarket.brandadminservice.modules.category.models.BusinessCategory;
import com.zmarket.brandadminservice.modules.category.services.BusinessCategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/business-category")
@WrapApiResponse
@RequiredArgsConstructor
public class BusinessCategoryController {
    private final BusinessCategoryService businessCategoryService;

    @PostMapping
    public BusinessCategory create(@RequestBody @Valid BusinessCategoryDto businessCategoryDto ){
        return businessCategoryService.createBusinessCategory(businessCategoryDto);
    }
    @GetMapping("/{id}")
    public BusinessCategory getById(@PathVariable Long id){
        return businessCategoryService.getById(id);
    }
    @GetMapping
    public List<BusinessCategory> getAll(){
        return businessCategoryService.getAll();
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        businessCategoryService.delete(id);
    }
    @PutMapping("/{id}")
    public BusinessCategory update(@PathVariable("id") Long id,@Valid @RequestBody BusinessCategoryDto businessCategoryDto) {
        return businessCategoryService.update(id,businessCategoryDto);
    }
}
