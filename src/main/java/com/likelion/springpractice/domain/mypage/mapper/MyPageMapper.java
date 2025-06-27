package com.likelion.springpractice.domain.mypage.mapper;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.mypage.dto.response.MyPageResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MyPageMapper {

  public MyPageResponse toResponse(
      List<BadgeResponse> badges,
      List<LikeResponse> likes,
      List<ReviewResponse> reviews) {

    return MyPageResponse.builder()
        .badges(badges)
        .likes(likes)
        .reviews(reviews)
        .build();
  }
}