package com.zmarket.brandadminservice.modules.brand.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zmarket.brandadminservice.modules.brand.enums.BusinessType;
import com.zmarket.brandadminservice.modules.category.models.BusinessCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "BRANDS")
public class Brand {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String businessNumber;

    private  String name;

    @Enumerated(EnumType.STRING)
    private BusinessType businessType;

    private String domainUrl;

    private String address;

    private String facebookUrl;

    private String instagramUrl;

    private String twitterUrl;

    private String logo;

    @ManyToOne
    private BusinessCategory category;

    @JsonIgnore
    @Column(name = "created_at")
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private Date updatedAt;

}
