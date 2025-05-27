package com.likelion.springpractice.domain.user.mapper;

import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component // 스프링 빈으로 등록되는 클래스 (DI 대상)
public class UserMapper {

  // User 엔티티를 SignUpResponse DTO로 변환하는 메서드
  public SignUpResponse toSignUpResponse(User user) {
    return SignUpResponse.builder()
        .userId(user.getId())
        .username(user.getUsername())
        .build(); // DTO 객체 생성
  }
}
