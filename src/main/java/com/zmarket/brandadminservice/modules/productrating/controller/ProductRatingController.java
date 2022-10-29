package com.zmarket.brandadminservice.modules.productrating.controller;

import com.zmarket.brandadminservice.annotations.WrapApiResponse;
import com.zmarket.brandadminservice.modules.productrating.dto.ProductRatingDto;
import com.zmarket.brandadminservice.modules.productrating.model.ProductRating;
import com.zmarket.brandadminservice.modules.productrating.service.ProductRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ratings")
@WrapApiResponse
@RequiredArgsConstructor
public class ProductRatingController {
    private final ProductRatingService productRatingService;

    @PostMapping("/{id}")
    public ProductRating rateProduct(@PathVariable Long id, @RequestBody @Valid ProductRatingDto request){
        return productRatingService.rateProduct(id,request);
    }

    @GetMapping("/{id}")
    public List<ProductRating> getAllProductRatingByProductId(@PathVariable Long id){
        return productRatingService.getAllProductRatingByProductId(id);
    }

}
