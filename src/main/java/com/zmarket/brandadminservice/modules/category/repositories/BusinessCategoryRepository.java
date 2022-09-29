package com.zmarket.brandadminservice.modules.category.repositories;

import com.zmarket.brandadminservice.modules.category.models.BusinessCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessCategoryRepository extends JpaRepository<BusinessCategory, Long> {
}