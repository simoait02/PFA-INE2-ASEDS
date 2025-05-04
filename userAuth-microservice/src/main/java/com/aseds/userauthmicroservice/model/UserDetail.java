package com.aseds.userauthmicroservice.model;

import com.aseds.userauthmicroservice.enums.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public record UserDetail(AbstractUser user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> user.getRole().name());
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

    public String getPhone() {
        return user.getPhone();
    }

    public Date getBirthDate() {
        return user.getBirthDate();
    }

    public Long getId() {
        return user.getId();
    }

    public String getProfilePictureUrl() {
        return user.getProfilePictureUrl();
    }

    @Override
    public AbstractUser user() {
        return this.user;
    }
    public Roles getRole() {
        return user.getRole();
    }
}
