package com.aseds.userauthmicroservice.services;

import com.aseds.userauthmicroservice.api.UserManagementClient;
import com.aseds.userauthmicroservice.model.RegisterRequest;
import com.aseds.userauthmicroservice.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class AuthService implements UserDetailsManager {
    private final UserManagementClient userManagementClient;
    private final UserDetailService userDetailService;

    @Autowired
    public AuthService(UserManagementClient userManagementClient, UserDetailService userDetailService) {
        this.userManagementClient = userManagementClient;
        this.userDetailService = userDetailService;
    }

    @Override
    public void createUser(UserDetails user) {
        try {
            RegisterRequest newUser = new RegisterRequest();
            newUser.setEmail(((UserDetail) user).getEmail());
            newUser.setBirthDate(((UserDetail) user).getBirthDate());
            newUser.setPassword(user.getPassword());
            newUser.setPhone(((UserDetail) user).getPhone());
            newUser.setUsername(user.getUsername());
            userManagementClient.registerUser(newUser);
        }catch (RestClientException e) {
            throw new RestClientException("Failed to register user", e);
        }
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return loadUserByUsername(username) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailService.loadUserByUsername(username);
    }
}
