package com.likelion.springpractice.domain.user.mapper;

import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public SignUpResponse tosignUpResponse(User user) {
    return SignUpResponse.builder()
        .userId(user.getUserId())
        .username(user.getUsername())
        .build();
  }
}
