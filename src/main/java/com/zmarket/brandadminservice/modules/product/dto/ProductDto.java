package com.zmarket.brandadminservice.modules.product.dto;

import com.zmarket.brandadminservice.modules.colour.dto.ColourDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class ProductDto {
    @NotBlank(message = "product name is required")
    private String productName;

    @NotBlank(message = "description is required")
    private String description;

    private long quantity;

    private boolean isNew;

    @NotBlank(message = "product ID is required")
    private String productId;

    @NotNull(message = "unit price is required")
    private BigDecimal unitPrice;

    private Set<String> imageUrls;

    private Long category;

    private Set<@Valid ColourDto> colours;
}
