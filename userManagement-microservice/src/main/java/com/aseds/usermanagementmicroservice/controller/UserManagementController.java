package com.aseds.usermanagementmicroservice.controller;

import com.aseds.usermanagementmicroservice.model.AbstractUser;
import com.aseds.usermanagementmicroservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-management/users")
public class UserManagementController {
    private final UserService userService;

    @Autowired
    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("{id}")
    public ResponseEntity<AbstractUser> updateUserById(@PathVariable Long id,@RequestBody AbstractUser user) {
        return ResponseEntity.ok(userService.partialUpdateUserById(id, user));
    }
    @GetMapping("{id}")
    public ResponseEntity<AbstractUser> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping
    public ResponseEntity<Page<AbstractUser>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("user deleted successfully");
    }

}
