package com.likelion.springpractice.domain.post.week05.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "GetPostRequest: 게시글 조회 요청 DTO")
public class GetPostRequest {

  @Schema(description = "게시글 제목", example = "1주차 세션???")
  private String title;

  @Schema(description = "게시글 내용", example = "내용내용내용")
  private String content;

  @Schema(description = "게시글 조회수", example = "123")
  private Long views;

}
