package com.likelion.springpractice.domain.like.mapper;

import com.likelion.springpractice.domain.like.dto.request.LikeRequest;
import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.entity.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

  public LikeResponse toLikeResponse(Like like) {
    return LikeResponse.builder()
        .foodName(like.getFood().getFoodName())
        .name(like.getUser().getName())
        .status(like.isStatus())
        .LikeNum(like.getFood().getLikeNum())
        .build();
  }
}
