package com.likelion.springpractice.domain.mission.mapper;

import com.likelion.springpractice.domain.mission.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.mission.dto.response.IntroductionResponse;
import com.likelion.springpractice.domain.mission.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.mission.entity.Role;
import com.likelion.springpractice.domain.mission.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public SignUpResponse toSignUpResponse(User user) {
    return SignUpResponse.builder()
        .userId(user.getId())
        .email(user.getEmail())
        .build();
  }

  public IntroductionResponse toIntroductionResponse(User user) {
    return IntroductionResponse.builder()
        .userId(user.getId())
        .username(user.getUsername())
        .introduction(user.getIntroduction())
        .build();
  }

  public User toEntity(SignUpRequest request, String encodedPassword) {
    return User.builder()
        .email(request.getEmail())
        .password(encodedPassword)
        .username(request.getUsername())
        .country(request.getCountry())  // ⭐ 반드시 포함
        .role(Role.User)
        .reviewCount(0)
        .build();
  }

}
