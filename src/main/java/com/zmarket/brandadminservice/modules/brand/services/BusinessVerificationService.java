package com.zmarket.brandadminservice.modules.brand.services;

import com.zmarket.brandadminservice.modules.brand.dtos.BusinessVerificationResponse;
import com.zmarket.brandadminservice.modules.brand.enums.BusinessType;

public interface BusinessVerificationService {
    BusinessVerificationResponse verify(String businessNumber, BusinessType businessType);
    boolean canApply(String slug );
}
