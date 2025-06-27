package com.likelion.springpractice.domain.mypage.dto.response;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "마이페이지 통합 응답 DTO")
public class MyPageResponse {

  @Schema(description = "내가 획득한 뱃지 목록")
  private List<BadgeResponse> badges;

  @Schema(description = "내가 좋아요한 음식 목록")
  private List<LikeResponse> likes;

  @Schema(description = "내가 작성한 리뷰 목록")
  private List<ReviewResponse> reviews;
}
