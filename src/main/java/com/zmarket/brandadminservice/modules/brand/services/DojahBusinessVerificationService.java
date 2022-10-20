package com.zmarket.brandadminservice.modules.brand.services;

import com.zmarket.brandadminservice.exceptions.BadRequestException;
import com.zmarket.brandadminservice.modules.brand.dtos.BusinessVerificationResponse;
import com.zmarket.brandadminservice.modules.brand.dtos.VerifyMeBusinessRequest;
import com.zmarket.brandadminservice.modules.brand.dtos.VerifyMeResponse;
import com.zmarket.brandadminservice.modules.brand.enums.BusinessType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DojahBusinessVerificationService implements BusinessVerificationService{

    private final RestTemplate restTemplate;

    @Value("${dojah.secret.key}")
    private String appSecretKey;

    @Value("${dojah.app.id}")
    private String appId;

    @Value("${dojah.base.url}")
    private String dojahBaseUrl;


    @Override
    public BusinessVerificationResponse verify(String businessNumber, BusinessType businessType,String businessName) {

        String url = dojahBaseUrl + "/api/v1/kyc/cac?rc_number=" + businessNumber + "&company_name=" +businessName;

        HttpEntity<VerifyMeBusinessRequest> entity = new HttpEntity<>(getHttpHeader());

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


    @Override
    public boolean canApply(String slug) {
        return "dojah".equalsIgnoreCase(slug);
    }

    private HttpHeaders getHttpHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization" , appSecretKey);
        headers.set("AppId" ,  appId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
