package com.zmarket.brandadminservice.modules.productrating.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zmarket.brandadminservice.modules.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "brand_product_ratings")
@AllArgsConstructor
@NoArgsConstructor
public class ProductRating {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //todo make starCount an enum
    private long starCount;

    private String comment;

    private long userId;

    private String userFullName;

    private boolean anonymous;

    @ManyToOne
    private Product product;

    @Column(name = "created_at")
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private Date updatedAt;

}
