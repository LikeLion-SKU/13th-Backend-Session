package com.likelion.springpractice.domain.post.week05.dto.reponse;

import com.likelion.springpractice.global.BaseTimeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "PostResponse: 게시글 응답 DTO")
public class PostResponse extends BaseTimeEntity {

  @Schema(description = "게시글 ID", example = "1")
  private Long postId;

  @Schema(description = "게시글 제목", example = "5주차 세션")
  private String title;

  @Schema(description = "게시글 내용", example = "세션 화이팅")
  private String content;

  @Schema(description = "게시글 생성 시간", example = "???")
  private LocalDateTime createdAt;



}
