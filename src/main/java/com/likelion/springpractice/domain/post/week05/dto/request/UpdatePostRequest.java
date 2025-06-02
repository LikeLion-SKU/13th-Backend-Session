package com.likelion.springpractice.domain.post.week05.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "UpdatePostRequest: 게시글 수정 요청 DTO")
public class UpdatePostRequest {

  @NotBlank(message = "제목을 비어있을 수 없습니다.")
  @Schema(description = "게시글 제목", example = "5주차 세션")
  private String title;

  @NotBlank(message = "내용은 비어있을 수 없습니다.")
  @Schema(description = "게시글 내용", example = "이번주 세션도 파이팅")
  private String content;
}
