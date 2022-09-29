package com.zmarket.brandadminservice.modules.brand.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyMeBusinessRequest {
    private long rcNumber;
    private String type;
}
