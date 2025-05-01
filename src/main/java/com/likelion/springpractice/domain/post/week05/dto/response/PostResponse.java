package com.likelion.springpractice.domain.post.week05.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {
  private Long postId;
  private String title;
  private String content;
}
