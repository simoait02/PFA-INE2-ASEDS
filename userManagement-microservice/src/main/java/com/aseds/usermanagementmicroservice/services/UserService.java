package com.aseds.usermanagementmicroservice.services;

import com.aseds.usermanagementmicroservice.model.AbstractUser;
import com.aseds.usermanagementmicroservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public AbstractUser partialUpdateUserById(Long id, AbstractUser updatedUser) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (updatedUser == null) {
            throw new IllegalArgumentException("Updated user data cannot be null");
        }

        updatedUser.setId(id);

        return userRepository.findById(id)
                .map(existingUser -> updateUserFields(existingUser, updatedUser))
                .orElseThrow(() -> createUserNotFoundException(id));
    }

    private AbstractUser updateUserFields(AbstractUser existingUser, AbstractUser updatedUser) {
        Optional.ofNullable(updatedUser.getEmail())
                .filter(email -> !email.equals(existingUser.getEmail()))
                .ifPresent(email -> {
                    validateEmailUniqueness(email, existingUser.getId());
                    existingUser.setEmail(email);
                });

        Optional.ofNullable(updatedUser.getPhone())
                .filter(phone -> !phone.equals(existingUser.getPhone()))
                .ifPresent(phone -> {
                    validatePhoneUniqueness(phone, existingUser.getId());
                    existingUser.setPhone(phone);
                });

        Optional.ofNullable(updatedUser.getUsername()).ifPresent(existingUser::setUsername);
        Optional.ofNullable(updatedUser.getPassword()).ifPresent(existingUser::setPassword);
        Optional.ofNullable(updatedUser.getProfilePictureUrl()).ifPresent(existingUser::setProfilePictureUrl);

        return userRepository.save(existingUser);
    }

    private void validateEmailUniqueness(String email, Long currentUserId) {
        userRepository.findUserByEmail(email)
                .ifPresent(user -> {
                    if (!user.getId().equals(currentUserId)) {
                        throw new IllegalArgumentException("Email already in use: " + email);
                    }
                });
    }

    private void validatePhoneUniqueness(String phone, Long currentUserId) {
        userRepository.findUserByPhone(phone)
                .ifPresent(user -> {
                    if (!user.getId().equals(currentUserId)) {
                        throw new IllegalArgumentException("Phone number already in use: " + phone);
                    }
                });
    }

    private RuntimeException createUserNotFoundException(Long id) {
        return new IllegalArgumentException("User not found with ID: " + id);
    }

    public Page<AbstractUser> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public AbstractUser findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));
    }
}
