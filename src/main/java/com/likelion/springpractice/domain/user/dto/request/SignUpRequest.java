package com.likelion.springpractice.domain.user.dto.request;

import com.likelion.springpractice.domain.user.entity.Nation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @NotBlank(message = "사용자 아이디 항목은 필수입니다.")
  @Schema(description = "사용자 아이디", example = "hamni0531")
  private String username;

  @NotBlank(message = "비밀번호 항목은 필수입니다.")
  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$",
      message = "비밀번호는 영문, 숫자, 특수문자 포함 8자 이상 작성해야 합니다."
  )
  @Schema(description = "비밀번호", example = "password123!")
  private String password;

  @NotBlank(message = "닉네임는 필수입니다.")
  @Schema(description = "닉네임", example = "햄햄햄")
  private String nickname;

  @Schema(description = "자기소개", example = "안녕하세요! 저는 열정적인 개발자입니다.")
  private String introduce;

  @Schema(description = "국적", example = "Korean")
  @NotNull(message = "국적은 필수입니다.")
  private Nation nation;


}
