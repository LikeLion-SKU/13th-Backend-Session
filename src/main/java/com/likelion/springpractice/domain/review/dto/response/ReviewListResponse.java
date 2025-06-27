package com.likelion.springpractice.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ReviewListResponse", description = "리뷰 목록 응답 DTO")
public class ReviewListResponse {

  @Schema(description = "리뷰 리스트")
  private List<ReviewResponse> reviews;
}