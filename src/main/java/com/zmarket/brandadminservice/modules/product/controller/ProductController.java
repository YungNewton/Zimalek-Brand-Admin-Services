package com.zmarket.brandadminservice.modules.product.controller;

import com.zmarket.brandadminservice.annotations.WrapApiResponse;
import com.zmarket.brandadminservice.modules.product.dto.ProductDto;
import com.zmarket.brandadminservice.modules.product.model.Product;
import com.zmarket.brandadminservice.modules.product.service.ProductServices;
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

@RestController
@RequestMapping("/product")
@WrapApiResponse
@RequiredArgsConstructor
public class ProductController {
    private final ProductServices productServices;


    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id){
        return productServices.getById(id);
    }

    @GetMapping("/product/{brandId}")
    public Page<Product> getProductByBrandId(@PathVariable Long brandId,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) String color,
                                                @RequestParam(required = false) String category,
                                                @RequestParam(required = false) BigDecimal price,
                                                @PageableDefault(size = 7 ) @SortDefault.SortDefaults({
                                                        @SortDefault(sort = "productName", direction = Sort.Direction.ASC),
                                                        @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
                                                }) Pageable pageable){
        return productServices.getProductByBrandId(brandId, start, end, name, color, category, price, pageable);
    }
    @GetMapping
    public Page<Product> getAll(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String color,
                                @RequestParam(required = false) String category,
                                @RequestParam(required = false) BigDecimal price,
                                @PageableDefault(size = 7 ) @SortDefault.SortDefaults({
                                        @SortDefault(sort = "productName", direction = Sort.Direction.ASC),
                                        @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
                                }) Pageable pageable){
        return productServices.getAll(start, end, name, color, category, price, pageable);
    }
}
