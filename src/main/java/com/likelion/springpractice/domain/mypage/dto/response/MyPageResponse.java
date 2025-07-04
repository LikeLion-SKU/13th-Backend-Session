package com.likelion.springpractice.domain.mypage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "MyPageResponse", description = "마이페이지 전체 정보")
public class MyPageResponse {

  @Schema(description = "이름", example = "고현서")
  private String username;

  @Schema(description = "이메일", example = "kohyun0223@skuniv.ac.kr")
  private String email;

  @Schema(description = "사용 언어", example = "korean")
  private String language;

  @Schema(description = "자기소개", example = "안녕하세요. 고현서입니다.")
  private String bio;
}