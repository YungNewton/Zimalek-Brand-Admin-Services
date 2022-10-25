package com.zmarket.brandadminservice.modules.product.model;

import com.zmarket.brandadminservice.modules.brand.models.Brand;
import com.zmarket.brandadminservice.modules.colour.model.Colour;
import com.zmarket.brandadminservice.modules.images.model.Image;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "brand_products")
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

    private String description;

    private String category;

    private boolean isNew;

    @ManyToOne
    private Brand brand;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "brand_product_image",
            joinColumns = @JoinColumn(name = "brand_product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> images;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "brand_product_colour",
            joinColumns = @JoinColumn(name = "brand_product_id"),
            inverseJoinColumns = @JoinColumn(name = "colour_id"))
    private Set<Colour> colours;

    private int totalOneStarRating;

    private int totalTwoStarRating;

    private int totalThreeStarRating;

    private int totalFourStarRating;

    private int totalFiveStarRating;

    private int totalStarRating;

    private int avgStarRating;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
