package com.likelion.springpractice.domain.user.dto.request;

import com.likelion.springpractice.domain.user.entity.Nation;
import com.likelion.springpractice.domain.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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

  @NotBlank(message = "이메일은 필수입니다.")
  @Email(message = "올바른 이메일 형식이어야 합니다.")
  @Schema(description = "이메일", example = "user@example.com")
  private String email;

  @NotBlank(message = "비밀번호 항목은 필수입니다.")
  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}$",
      message = "비밀번호는 최소 8자 이상, 숫자 및 특수문자를 포함해야 합니다.")
  @Schema(description = "비밀번호", example = "password123!")
  private String password;

  @NotBlank(message = "닉네임은 필수입니다.")
  @Schema(description = "닉네임", example = "나옹이")
  private String nickname;

  @Schema(description = "국가 코드", example = "KO")
  private Nation nation;

  @Schema(description = "자기소개", example = "안녕하세요. 매운 걸 좋아합니다.")
  private String selfIntro;

  @Schema(description = "사용자 권한", example = "USER")
  private Role role;
}
