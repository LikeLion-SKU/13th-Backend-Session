package com.likelion.springpractice.domain.auth.dto.response;

import com.likelion.springpractice.domain.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "LoginResponse DTO", description = "사용자 로그인에 대한 응답 반환")
public class LoginResponse {

  // refresh 토큰은..

  @Schema(description = "사용자 Access Token")
  private String accessToken;

  @Schema(description = "사용자 고유번호", example = "1")
  private Long userId;

  @Schema(description = "사용자 이메일(아이디)", example = "seohyeon1129@example.com")
  private String email;

  @Schema(description = "사용자 닉네임", example = "심서현")
  private String username;

  @Schema(description = "사용자 국적", example = "KOREA")
  private String nationality;

  @Schema(description = "사용자 자기소개", example = "나는야 불맛 전문가")
  private String comment;

  @Schema(description = "사용자 권한", example = "USER")
  private Role role;

  @Schema(description = "사용자 Access Token 만료 시간", example = "1800000")
  private Long expirationTime;
}
