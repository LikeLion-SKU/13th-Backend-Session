package com.likelion.springpractice.domain.user.dto.response;

import com.likelion.springpractice.domain.user.entity.Nation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "UserResponse DTO", description = "사용자 정보에 대한 응답 반환")
public class UserResponse {

  @Schema(description = "사용자 ID", example = "1")
  private Long id;

  @Schema(description = "사용자 별명", example = "나옹")
  private String nickname;

  @Schema(description = "국적(언어)", example = "KO")
  private Nation nation;

  @Schema(description = "자기소개", example = "안뇽 나경이얌")
  private String selfIntro;
}
