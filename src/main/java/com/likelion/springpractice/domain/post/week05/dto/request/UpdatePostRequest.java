package com.likelion.springpractice.domain.post.week05.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdatePostRequest {

  @NotBlank
  private String title;
  @NotBlank
  private String content;
}
