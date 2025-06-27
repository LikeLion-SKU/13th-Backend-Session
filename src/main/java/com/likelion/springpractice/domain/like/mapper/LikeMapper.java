package com.likelion.springpractice.domain.like.mapper;

import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {
  public LikeResponse toResponse(Long foodId, int likeCount, boolean liked) {
    return LikeResponse.builder()
        .foodId(foodId)
        .likeCount(likeCount)
        .liked(liked)
        .build();
  }
}