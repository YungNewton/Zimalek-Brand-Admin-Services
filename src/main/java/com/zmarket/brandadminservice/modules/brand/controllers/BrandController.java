package com.zmarket.brandadminservice.modules.brand.controllers;


import com.zmarket.brandadminservice.annotations.WrapApiResponse;
import com.zmarket.brandadminservice.modules.brand.dtos.BrandDto;
import com.zmarket.brandadminservice.modules.brand.models.Brand;
import com.zmarket.brandadminservice.modules.brand.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    public Page<Brand> getAll(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String color,
                              @RequestParam(required = false) String category,
                              @RequestParam(required = false) BigDecimal price,
                              @PageableDefault(size = 7 ) @SortDefault.SortDefaults({
                                      @SortDefault(sort = "productName", direction = Sort.Direction.ASC),
                                      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
                              }) Pageable pageable){
        return brandService.getAll(start, end, name, color, category, price, pageable);
    }
    @PutMapping("/{id}")
    public Brand update(@PathVariable("id") Long id,@Valid @RequestBody BrandDto brandDto) {
        return brandService.update(id,brandDto);
    }
}
