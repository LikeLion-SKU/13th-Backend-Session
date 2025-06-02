package com.likelion.springpractice.domain.post.week05.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "CreatePostRequest: 게시글 생성 요청 DTO")
public class CreatePostRequest {

    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    @Schema(description = "게시글 제목", example = "1주차 세션???")
    private String title;

    @NotBlank(message = "내용은 비어 있을 수 없습니다.")
    @Schema(description = "게시글 내용", example = "내용내용내용내용")
    private String content;
}
