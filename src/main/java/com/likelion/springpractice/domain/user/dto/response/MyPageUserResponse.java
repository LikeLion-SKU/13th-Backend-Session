package com.likelion.springpractice.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "마이페이지 내 기본 정보 응답 DTO")
public class MyPageUserResponse {

  @Schema(description = "닉네임", example = "맵찔이")
  private String username;

  @Schema(description = "이메일", example = "mapuser@example.com")
  private String email;

  @Schema(description = "국적", example = "대한민국")
  private String national;

  @Schema(description = "자기소개", example = "매운 거 잘 못 먹지만 도전 중!")
  private String introduction;
}
