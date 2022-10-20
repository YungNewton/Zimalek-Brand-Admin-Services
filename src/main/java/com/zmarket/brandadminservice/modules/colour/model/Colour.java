package com.zmarket.brandadminservice.modules.colour.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "brand_colours")
@AllArgsConstructor
@NoArgsConstructor
public class Colour {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String code;

    @JsonIgnore
    @Column(name = "created_at")
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private Date updatedAt;

    public Colour(String name, String code, Date createdAt) {
        this.name = name;
        this.code = code;
        this.createdAt = createdAt;
    }
}
