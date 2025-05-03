package com.aseds.userauthmicroservice.api;

import com.aseds.userauthmicroservice.model.AbstractUser;
import com.aseds.userauthmicroservice.model.RegisterRequest;
import com.aseds.userauthmicroservice.model.UserDTO;
import com.aseds.userauthmicroservice.model.UserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class UserManagementClient {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementClient.class);

    private static final String USERS_ENDPOINT = "/users/{identifier}";
    private static final String REGISTER_ENDPOINT = "/register";
    private final PasswordEncoder passwordEncoder;
    private final String baseUrl;
    private final RestTemplate restTemplate;

    public UserManagementClient(PasswordEncoder passwordEncoder, @Value("${user.management.api.base-url}") String baseUrl,
                                RestTemplate restTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    public Optional<UserDetails> getUserByIdentifier(String identifier) {
        try {
            AbstractUser user = restTemplate.getForObject(
                    baseUrl + USERS_ENDPOINT,
                    AbstractUser.class,
                    identifier
            );
            return Optional.ofNullable(user).map(UserDetail::new);
        } catch (RestClientException e) {
            logger.error("Error fetching user with identifier: {}", identifier, e);
            throw new UserManagementException("Failed to retrieve user", e);
        }
    }

    public void registerUser(RegisterRequest registerRequest) {
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        try {
            restTemplate.postForObject(
                    UriComponentsBuilder.fromUriString(baseUrl)
                            .path(REGISTER_ENDPOINT)
                            .build()
                            .toUriString(),
                    registerRequest,
                    UserDTO.class
            );
        } catch (HttpClientErrorException.BadRequest e) {
            String responseMessage = e.getResponseBodyAsString();
            logger.error("Error registering user: {}", registerRequest.getUsername());
            throw new IllegalArgumentException(responseMessage, e);

        } catch (RestClientException e) {
            logger.error("Unexpected error during user registration: {}", registerRequest.getUsername());
            throw new UserManagementException("Failed to register user", e);
        }
    }

    public static class UserManagementException extends RuntimeException {
        public UserManagementException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}