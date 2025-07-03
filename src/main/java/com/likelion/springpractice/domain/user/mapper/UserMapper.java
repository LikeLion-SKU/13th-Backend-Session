package com.likelion.springpractice.domain.user.mapper;

import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdateResponse;
import com.likelion.springpractice.domain.user.dto.response.UserResponse;
import com.likelion.springpractice.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public SignUpResponse toSignUpResponse(User user) {
    return SignUpResponse.builder()
        .userId(user.getId())
        .username(user.getUsername())
        .build();
  }

  public UpdateResponse toUpdateResponse(User user) {
    return UpdateResponse.builder()
        .userId(user.getId())
        .username(user.getUsername())
        .build();
  }

  public UserResponse toUserResponse(User user) {
    return UserResponse.builder()
        .userId(user.getId())
        .username(user.getUsername())
        .name(user.getName())
        .language(user.getLanguage())
        .introduce(user.getIntroduce())
        .build();
  }


}
