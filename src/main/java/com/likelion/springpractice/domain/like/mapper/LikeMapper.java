package com.likelion.springpractice.domain.like.mapper;

import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.entity.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

  public LikeResponse toLikeResponse(Like like) {
    return LikeResponse.builder()
        .userId(like.getUser().getId())
        .foodId(like.getFood().getFoodId())
        .foodName(like.getFood().getFoodName())
        .foodDescription(like.getFood().getFoodDescription())
        .build();
  }

}
