package com.zmarket.brandadminservice.modules.images.repository;

import com.zmarket.brandadminservice.modules.images.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
