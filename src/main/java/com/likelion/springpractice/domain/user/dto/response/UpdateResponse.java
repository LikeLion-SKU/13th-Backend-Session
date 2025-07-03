package com.likelion.springpractice.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title="UpdateResponse DTO", description = "사용자 정보 변경에 대한 응답 변환")
public class UpdateResponse {
  @Schema(description = "정보 변경된 사용자 ID", example = "1")
  private Long userId;

  @Schema(description = "정보 변경된 사용자 아이디", example = "heejun0109")
  private String username;




}
