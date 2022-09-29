package com.zmarket.brandadminservice.modules.brand.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusinessVerificationResponse {
    private boolean verified;
    private String name;
    private String businessNumber;

    public BusinessVerificationResponse(boolean verified) {
        this.verified = verified;
    }

    public BusinessVerificationResponse(boolean verified, String name, String businessNumber) {
        this.verified = verified;
        this.name = name;
        this.businessNumber = businessNumber;
    }
}
