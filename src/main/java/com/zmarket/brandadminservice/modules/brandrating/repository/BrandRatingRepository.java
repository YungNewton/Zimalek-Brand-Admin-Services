package com.zmarket.brandadminservice.modules.brandrating.repository;

import com.zmarket.brandadminservice.modules.brand.models.Brand;
import com.zmarket.brandadminservice.modules.brandrating.model.BrandRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRatingRepository extends JpaRepository<BrandRating, Long> {
    List<BrandRating> findByBrand(Brand brand);
}
