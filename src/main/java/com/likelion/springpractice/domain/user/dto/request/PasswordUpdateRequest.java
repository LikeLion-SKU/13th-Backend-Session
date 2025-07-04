package com.likelion.springpractice.domain.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "비밀번호 변경 요청 DTO")
public class PasswordUpdateRequest {

  @Schema(description = "현재 비밀번호", example = "hamni0531!")
  private String currentPassword;

  @Schema(description = "새로운 비밀번호", example = "ham0531!")
  private String newPassword;
}
