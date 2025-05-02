package com.likelion.springpractice.domain.post.week05.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "PostResponse: 게시글 응답 DTO")
public class PostResponse {

  @Schema(description = "게시글 ID", example = "1")
  private Long id;

  @Schema(description = "게시글 제목", example = "5주차 세션")
  private String title;

  @Schema(description = "게시글 내용", example = "5주차 세션 내용 응답")
  private String content;

  @Schema(description = "게시글 작성 시각", example = "2025-05-01T02:08:00")
  private LocalDateTime createdAt;

  @Schema(description = "게시글 조회수", example = "123")
  private Long views;
}
