package com.aseds.userauthmicroservice.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class UserDetail implements UserDetails {
    private final AbstractUser user;

    public UserDetail(AbstractUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()->user.getRole().name());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    public String getEmail() {
        return user.getEmail();
    }
    public String getPhone(){
        return user.getPhone();
    }

    public Date getBirthDate() {
        return user.getBirthDate();
    }
}
