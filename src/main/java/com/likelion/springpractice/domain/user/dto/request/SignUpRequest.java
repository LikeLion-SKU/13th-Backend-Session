package com.likelion.springpractice.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "SignUpRequest DTO", description = "사용자 회원가입을 위한 데이터 전송")
public class SignUpRequest {

  /*
    @NotBlank(message = "사용자 아이디 항목은 필수입니다.")
    @Schema(description = "사용자 아이디", example = "seohyeon1129")
    private String username;
  */

  @NotBlank(message = "사용자 아이디 항목은 필수입니다.")
  @Schema(description = "사용자 아이디", example = "seohyeon1129@example.com")
  private String email;   // 이메일(아이디)

  @NotBlank(message = "비밀번호 항목은 필수입니다.")
  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}$",
      message = "비밀번호는 최소 8자 이상, 숫자 및 특수문자를 포함해야 합니다.")
  @Schema(description = "비밀번호", example = "password123!")
  private String password;

  @NotBlank(message = "사용자 닉네임은 필수입니다.")
  @Schema(description = "사용자 닉네임", example = "심서현")
  private String username;

  @NotBlank(message = "국적은 필수입니다.")
  @Schema(description = "사용자 국적", example = "KOREA")
  private String nationality;

  // 자기소개는 회원가입 시 X
  // Role 필요 없을 듯
}

