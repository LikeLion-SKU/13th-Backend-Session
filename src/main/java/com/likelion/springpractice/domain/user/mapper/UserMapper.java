package com.likelion.springpractice.domain.user.mapper;

import com.likelion.springpractice.domain.user.dto.response.UserResponse;
import com.likelion.springpractice.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public static UserResponse toUserResponse(User user) {
    return UserResponse.builder()
        .userId(user.getUserId())
        .username(user.getUsername())
        .name(user.getName())
        .language(user.getLanguage().getDisplayName())
        .introduction(user.getIntroduction())
        .build();
  }

}
