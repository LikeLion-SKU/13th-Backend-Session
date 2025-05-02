package com.likelion.springpractice.domain.post.week05.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "PostResponse: 게시글 응답 DTO")
public class PostResponse {

    @Schema(description = "게시글 ID", example = "1")
    private Long postId;

    @Schema(description = "게시글 제목", example = "5주차 세션")
    private String title;

    @Schema(description = "게시글 내용", example = "이번주 세션도 화이팅!")
    private String content;
}
