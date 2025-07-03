package com.likelion.springpractice.domain.user.dto.response;

import com.likelion.springpractice.domain.Like.dto.response.LikeFoodResponse;
import com.likelion.springpractice.domain.Review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "마이페이지 통합 응답 DTO")
public class MyPageResponse {

  @Schema(description = "내 기본 정보")
  private MyPageUserResponse userInfo;

  @Schema(description = "내가 작성한 후기 목록")
  private List<ReviewResponse> reviews;

  @Schema(description = "내가 좋아요한 음식 목록")
  private List<LikeFoodResponse> likes;

  @Schema(description = "내가 획득한 뱃지 목록")
  private List<BadgeResponse> badges;
}
