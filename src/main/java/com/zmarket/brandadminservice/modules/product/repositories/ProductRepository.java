package com.zmarket.brandadminservice.modules.product.repositories;

import com.zmarket.brandadminservice.modules.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductName(String productName);
    Optional<Product> findByIdAndUserId(Long id, Long UserId);
}
