package com.likelion.springpractice.domain.mission.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

  private Long reviewId;
  private String content;
  private int rating;
  private String username;
  private String foodName;
  private LocalDateTime createdAt;
}
