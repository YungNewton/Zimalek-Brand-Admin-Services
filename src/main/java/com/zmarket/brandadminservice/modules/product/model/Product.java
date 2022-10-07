package com.zmarket.brandadminservice.modules.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zmarket.brandadminservice.modules.brand.models.Brand;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "brand_product")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productId;
    private String productName;
    private BigDecimal unitPrice;
    private long quantity;
    private Long userId;
    private Long brandId;
    private List<String> images;
    private String description;
    private boolean isNew;


    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
