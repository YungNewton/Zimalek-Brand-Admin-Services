package com.zmarket.brandadminservice.modules.productrating.repository;

import com.zmarket.brandadminservice.modules.product.model.Product;
import com.zmarket.brandadminservice.modules.productrating.model.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {

    List<ProductRating> findByProduct(Product product);


}
