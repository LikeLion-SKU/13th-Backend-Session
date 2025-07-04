package com.likelion.springpractice.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "UserResponse DTO", description = "사용자 회원 정보 수정에 대한 응답 변환")
public class UserResponse {

  private final String name;
  private final String nationality;

}
