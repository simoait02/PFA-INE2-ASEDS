package com.aseds.videomicroservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserManagementClient {
    private static final String VERIFY_USER_ENDPOINT= "/exist/";
    @Value("${jwt.internal}")
    private String internalSecretKey;
    private final String baseUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public UserManagementClient(@Value("${user.management.api.base-url}") String baseUrl, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }


    public boolean isUserExist(Long id) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-INTERNAL-SECRET", internalSecretKey);
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<Boolean> response = restTemplate.exchange(
                    baseUrl + VERIFY_USER_ENDPOINT + id,
                    HttpMethod.POST,
                    requestEntity,
                    Boolean.class
            );

            return Boolean.TRUE.equals(response.getBody());
        } catch (RestClientException e) {
            return false;
        }
    }
}
