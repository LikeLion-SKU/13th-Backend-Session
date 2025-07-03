package com.likelion.springpractice.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "SignUpResponse DTO", description = "사용자 회원가입에 대한 응답 변환")
public class SignUpResponse {

  @Schema(description = "회원가입된 사용자 고유번호", example = "1")
  private Long userId;

  @Schema(description = "회원가입 사용자 아이디", example = "seohyeon1129@example.com")
  private String email;   // 이메일(아이디)

  @Schema(description = "회원가입 사용자 닉네임", example = "심서현")
  private String username;   // 닉네임

  @Schema(description = "회원가입 사용자 국적", example = "KOREA")
  private String nationality;   // 국적

/*
  @Schema(description = "회원가입 사용자 아이디", example = "seohyeon1129")
  private String username;
 */
}
