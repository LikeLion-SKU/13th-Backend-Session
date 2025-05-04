package com.likelion.springpractice.domain.post.week05.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "UpdatePostRequest: 게시글 수정 요청 DTO")
public class UpdatePostRequest {

  @Schema(description = "게시글 제목", example = "5주차 세션")
  private String title;

  @Schema(description = "게시글 내용", example = "이번주 세션도 화이팅!")
  private String content;

  //게시글 수정 시에는, 기존에 있던 값이 유지되어야 하므로 우리가 값을 넣어줄 필요 없음!!
  @Schema(description = "조회수", example = "1")
  private Long views;
}
