package com.zmarket.brandadminservice.modules.brandrating.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zmarket.brandadminservice.modules.brand.models.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "brand_ratings")
@AllArgsConstructor
@NoArgsConstructor
public class BrandRating {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int starCount;

    private String comment;

    private long userId;

    private String userFullName;

    private boolean anonymous;

    @ManyToOne
    private Brand brand;

    @Column(name = "created_at")
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private Date updatedAt;
}
