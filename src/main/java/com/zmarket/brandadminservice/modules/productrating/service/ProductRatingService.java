package com.zmarket.brandadminservice.modules.productrating.service;

import com.zmarket.brandadminservice.modules.productrating.dto.ProductRatingDto;
import com.zmarket.brandadminservice.modules.productrating.model.ProductRating;

import java.util.List;

public interface ProductRatingService {
    ProductRating rateProduct(Long id, ProductRatingDto request);

    List<ProductRating> getAllProductRatingByProductId(Long id);
}
