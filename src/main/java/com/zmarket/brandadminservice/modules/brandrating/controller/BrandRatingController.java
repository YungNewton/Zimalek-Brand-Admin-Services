package com.zmarket.brandadminservice.modules.brandrating.controller;

import com.zmarket.brandadminservice.annotations.WrapApiResponse;
import com.zmarket.brandadminservice.modules.brandrating.dto.BrandRatingDto;
import com.zmarket.brandadminservice.modules.brandrating.model.BrandRating;
import com.zmarket.brandadminservice.modules.brandrating.service.BrandRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ratings")
@WrapApiResponse
@RequiredArgsConstructor
public class BrandRatingController {
    private final BrandRatingService brandRatingService;

    @PostMapping("/{id}")
    public BrandRating rateBrand(@PathVariable Long id, @RequestBody @Valid BrandRatingDto request){
        return brandRatingService.rateBrand(id,request);
    }

    @GetMapping("/{id}")
    public List<BrandRating> getAllBrandRatingByBrandId(@PathVariable Long id){
        return brandRatingService.getAllBrandRatingByBrandId(id);
    }
}
