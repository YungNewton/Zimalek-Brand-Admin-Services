package com.zmarket.brandadminservice.modules.colour.repository;

import com.zmarket.brandadminservice.modules.colour.model.Colour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColourRepository extends JpaRepository<Colour, Long> {
}