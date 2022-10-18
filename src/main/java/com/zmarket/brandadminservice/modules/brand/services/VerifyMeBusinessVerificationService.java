package com.zmarket.brandadminservice.modules.brand.services;

import com.zmarket.brandadminservice.exceptions.BadRequestException;
import com.zmarket.brandadminservice.modules.brand.dtos.BusinessVerificationResponse;
import com.zmarket.brandadminservice.modules.brand.dtos.VerifyMeBusinessRequest;
import com.zmarket.brandadminservice.modules.brand.dtos.VerifyMeResponse;
import com.zmarket.brandadminservice.modules.brand.enums.BusinessType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class VerifyMeBusinessVerificationService implements BusinessVerificationService {


    private final RestTemplate restTemplate;

    @Value("${verifyme.base.url}")
    private String verifymeBaseUrl;

    @Value("${verifyme.secret.key}")
    private String verifymeSecretKey;

    @Override
    public BusinessVerificationResponse verify(String businessNumber, BusinessType businessType,String businessName) {

        String url = verifymeBaseUrl + "/verifications/identities/cac";
        VerifyMeBusinessRequest request = getVerifyBusinessRequest(businessNumber, businessType);

        if (Objects.isNull(request)) {
            throw new BadRequestException("Invalid Business registration number");
        }

        HttpEntity<VerifyMeBusinessRequest> entity = new HttpEntity<>(request,
                getHttpHeader(verifymeSecretKey));

        try {
            ResponseEntity<VerifyMeResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, VerifyMeResponse.class);
            return new BusinessVerificationResponse(true, Objects.requireNonNull(responseEntity.getBody()).getData().getCompanyName(), String.valueOf(Objects.requireNonNull(responseEntity.getBody()).getData().getRcNumber()));
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unknown error occurred, please try again later");
        }

    }

    private VerifyMeBusinessRequest getVerifyBusinessRequest(String businessNumber, BusinessType businessType) {
        businessNumber = businessNumber.toLowerCase().replaceAll("rc", "");

        try {
            return new VerifyMeBusinessRequest(Long.parseLong(businessNumber), getBusinessType(businessType));
        }catch (Exception e) {
            return null;
        }
    }

    private String getBusinessType(BusinessType businessType) {
        if (businessType == BusinessType.BS) {
            return businessType.getValue().toLowerCase();
        }

        if (businessType == BusinessType.RC) {
            return "limited_company";
        }

        return "incorprated_trustee";
    }

    @Override
    public boolean canApply(String slug) {
        return "verifyme".equalsIgnoreCase(slug);
    }

    private HttpHeaders getHttpHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization" , "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
