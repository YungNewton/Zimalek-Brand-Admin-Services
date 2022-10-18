package com.zmarket.brandadminservice.modules.colour.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColourDto implements Serializable {

    @NotBlank(message = "colour name is required")
    private String name;

    @NotBlank(message = "colour code is required")
    private String code;
}
