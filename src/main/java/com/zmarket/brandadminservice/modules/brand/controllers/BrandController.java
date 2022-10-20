package com.zmarket.brandadminservice.modules.brand.controllers;


import com.zmarket.brandadminservice.annotations.WrapApiResponse;
import com.zmarket.brandadminservice.modules.brand.dtos.BrandDto;
import com.zmarket.brandadminservice.modules.brand.models.Brand;
import com.zmarket.brandadminservice.modules.brand.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/brand")
@WrapApiResponse
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public Brand create(@RequestBody @Valid BrandDto brandDto ){
        return brandService.createBrand(brandDto);
    }
    @GetMapping("/{id}")
    public Brand getById(@PathVariable Long id){
        return brandService.getById(id);
    }
    @GetMapping
    public List<Brand> getAll(){
        return brandService.getAll();
    }

    @PutMapping("/{id}")
    public Brand update(@PathVariable("id") Long id,@Valid @RequestBody BrandDto brandDto) {
        return brandService.update(id,brandDto);
    }
}
