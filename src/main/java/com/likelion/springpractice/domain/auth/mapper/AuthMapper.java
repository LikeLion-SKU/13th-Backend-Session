package com.likelion.springpractice.domain.auth.mapper;

import com.likelion.springpractice.domain.auth.dto.response.LoginResponse;
import com.likelion.springpractice.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

  public LoginResponse toLoginResponse(User user, String accessToken, Long expirationTime) {
    return LoginResponse.builder()  // Entity -> Response DTO
        .accessToken(accessToken)
        .userId(user.getId())
        .email(user.getEmail())
        .username(user.getUsername())
        .nationality(user.getNationality())
        .comment(user.getComment())
        .role(user.getRole())
        .expirationTime(expirationTime)
        .build();
  }
}