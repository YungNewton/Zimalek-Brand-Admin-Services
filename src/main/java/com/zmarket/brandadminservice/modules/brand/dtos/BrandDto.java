package com.zmarket.brandadminservice.modules.brand.dtos;

import com.zmarket.brandadminservice.modules.brand.enums.BusinessType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BrandDto implements Serializable {
    @NotBlank(message = "Business number is required")
    private String businessNumber;

    @NotNull(message = "Business type is required")
    private BusinessType businessType;

    @NotBlank(message = "Business domain is required")
    private String domainUrl;

    @NotBlank(message = "Business address is required")
    private String address;

    private String facebookUrl;

    private String instagramUrl;

    private String twitterUrl;

    private String logo;

    @NotNull(message = "Category is required")
    private Long categoryCategoryId;
}
