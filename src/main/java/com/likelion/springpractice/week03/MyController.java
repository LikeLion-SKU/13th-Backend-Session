package com.likelion.springpractice.week03;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class MyController {

    @PostMapping("users")
    public String createUser() {
        return "This is create user";
    }

    @GetMapping("users/{userId}")
    public String getUsers(@PathVariable Long userId) {
        return "This is get user information by " + userId;
    }

    @PatchMapping("users/{userId}")
    public String updatePatchUser(@PathVariable Long userId) {
        return "This is update Patch user by " + userId;
    }

    @PutMapping("users/{userId}")
    public String updatePutUser(@PathVariable Long userId) {
        return "This is update Put user by " + userId;
    }

    @DeleteMapping("users/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        return "This is delete user by " + userId;
    }
}
