package com.zmarket.brandadminservice.modules.brand.dtos;

import com.zmarket.brandadminservice.modules.brand.enums.BusinessType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateBrandDto implements Serializable {

    @NotNull(message = "categoryId is required")
    private Long categoryId;

    private BusinessType businessType;

    @NotBlank(message = "Business address is required")
    private String address;

    private String facebookUrl;

    private String instagramUrl;

    private String twitterUrl;

    private String logo;
}
