package com.zmarket.brandadminservice.modules.brand.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyMeResponse {
    private String status;
    private int statusCode;
    private String error;
    private String message;
    private VerifyMeBusinessData data;
}
