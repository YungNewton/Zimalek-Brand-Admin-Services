package com.zmarket.brandadminservice.modules.brand.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BusinessVerificationFactory {
    private List<BusinessVerificationService> businessVerificationServiceList;

    public BusinessVerificationService resolve(String slug) {
        return businessVerificationServiceList.stream().filter(m -> m.canApply(slug)).findFirst().orElse(null);
    }

}
