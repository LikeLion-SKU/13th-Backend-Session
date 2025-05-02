package com.likelion.springpractice.domain.post.week05.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "UpdatePostRequest: 게시글 수정 요청 DTO")
public class UpdatePostRequest {

  @Schema(description = "게시글 제목", example = "5주차 세션")
  private String title;

  @Schema(description = "게시글 내용", example = "5주차 세션 내용 요약 수정")
  private String content;
}
