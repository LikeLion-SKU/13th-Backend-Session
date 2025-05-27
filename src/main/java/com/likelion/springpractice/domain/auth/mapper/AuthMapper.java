package com.likelion.springpractice.domain.auth.mapper;

import com.likelion.springpractice.domain.auth.dto.response.LoginResponse;
import com.likelion.springpractice.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component // Spring Bean으로 등록
public class AuthMapper {

  public LoginResponse toLoginResponse(User user, String accessToken, Long expirationTime) {
    return LoginResponse.builder()
        .accessToken(accessToken) // 발급된 액세스 토큰
        .userId(user.getId()) // 사용자 ID
        .username(user.getUsername()) // 사용자 이름
        .role(user.getRole()) // 사용자 권한
        .expirationTime(expirationTime) // 토큰 만료 시간
        .build(); // DTO 반환
  }
}
