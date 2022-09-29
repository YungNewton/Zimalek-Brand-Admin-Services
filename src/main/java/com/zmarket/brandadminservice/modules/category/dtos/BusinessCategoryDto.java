package com.zmarket.brandadminservice.modules.category.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusinessCategoryDto implements Serializable {
    private String name;
    private String description;
}
