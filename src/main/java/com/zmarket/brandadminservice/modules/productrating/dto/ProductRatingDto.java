package com.zmarket.brandadminservice.modules.productrating.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProductRatingDto {
    @Min(value = 1, message = "start count must be greater than zero")
    @Max(value = 2, message = "start count can't be less than 5")
    private int starCount;

    @Size(min = 3, message = "Comment must be greater than 3 words")
    private String comment;

    private boolean anonymous;

}
