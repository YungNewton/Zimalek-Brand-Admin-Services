package com.zmarket.brandadminservice.modules.category.dtos;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class BusinessCategoryDto{
    @NotBlank(message = "name is required")
    private String name;
   @NotBlank(message = "description is required")
    private String description;
}
