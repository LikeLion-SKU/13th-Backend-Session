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
@Schema(title = "SignUpRequest DTO", description = "사용자 정보 변경을 위한 데이터 전송")
public class UpdateRequest {

  @NotBlank(message = "사용자 아이디 항목은 필수입니다.")
  @Schema(description = "사용자 아이디", example = "abc@naver.com")
  private String username;


  @Schema(description = "이름(별명)", example = "아이러브스파이시")
  private String name;

  @Schema(description = "언어 선택", example = "English")
  private String language;

  @Schema(description = "자기 소개", example = "Hello!")
  private String introduce;

}
