package com.zmarket.brandadminservice.modules.brandrating.service;

import com.zmarket.brandadminservice.modules.brandrating.dto.BrandRatingDto;
import com.zmarket.brandadminservice.modules.brandrating.model.BrandRating;

import java.util.List;

public interface BrandRatingService {
    BrandRating rateBrand(Long id, BrandRatingDto request);

    List<BrandRating> getAllBrandRatingByBrandId(Long id);
}
