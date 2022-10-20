package com.zmarket.brandadminservice.modules.category.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "categories")
public class BusinessCategory {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @JsonIgnore
    @Column(name = "created_at")
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private Date updatedAt;

}
