package com.likelion.springpractice.domain.mypage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "MyPageResponse", description = "마이페이지 전체 정보")
public class MyPageResponse {

  @Schema(description = "이름", example = "홍길동")
  private String username;

  @Schema(description = "이메일", example = "test@example.com")
  private String email;

  @Schema(description = "국적", example = "KOREA")
  private String language;

  @Schema(description = "자기소개", example = "안녕하세요, 개발자입니다.")
  private String bio;
}
