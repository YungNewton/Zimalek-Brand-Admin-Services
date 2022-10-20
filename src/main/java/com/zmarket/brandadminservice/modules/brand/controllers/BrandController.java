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
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/brands")
@WrapApiResponse
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;


    @GetMapping("/{id}")
    public Brand getById(@PathVariable Long id){
        return brandService.getById(id);
    }

    @GetMapping("/handle/{handle}")
    public Brand getBrandByHandle(@PathVariable String handle){
        return brandService.getBrandByHandle(handle);
    }

    @GetMapping
    public Page<Brand> getAll(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                              @RequestParam(required = false) String category,
                              @PageableDefault(size = 7 ) @SortDefault.SortDefaults({
                                      @SortDefault(sort = "name", direction = Sort.Direction.ASC),
                                      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
                              }) Pageable pageable){
        return brandService.getAll(start, end, category, pageable);
    }


}
