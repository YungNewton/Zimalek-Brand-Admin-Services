package com.zmarket.brandadminservice.util;

import com.zmarket.brandadminservice.dtos.UserApiResponse;
import com.zmarket.brandadminservice.modules.security.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestUtil {

    private final RestTemplate restTemplate;

    @Value("${user.service.base.url}")
    private String accountApiBaseUrl;

    @SneakyThrows
    public User getUserByToken(String token) {

        String url = accountApiBaseUrl + "/user";

        HttpEntity<?> entity = new HttpEntity<>(getHttpHeader(token));

        try {
            ResponseEntity<UserApiResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, UserApiResponse.class);
            return Objects.requireNonNull(responseEntity.getBody()).getResponseBody();
        } catch (HttpClientErrorException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An unknown error occurred");
        }

    }


    private HttpHeaders getHttpHeader(UUID clientId, String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID" , String.valueOf(clientId));
        headers.set("Authorization" , "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private HttpHeaders getHttpHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization" , "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
