package com.zmarket.brandadminservice.modules.brand.repositories;

import com.zmarket.brandadminservice.modules.brand.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByName(String name);
    Optional<Brand> findByIdAndUserId(Long id, Long UserId);
    List<Brand> findByUserId(Long userId);

    Optional<Brand> findFirstByHandle(String handle);


}