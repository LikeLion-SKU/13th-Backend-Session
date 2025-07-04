package com.likelion.springpractice.domain.Like.mapper;

import com.likelion.springpractice.domain.Like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.Like.entity.Like;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

  public LikeResponse toLikeResponse(Like like) {
    return LikeResponse.builder()
        .id(like.getId())
        .foodId(like.getFood().getId())
        .foodName(like.getFood().getName())
        .isDeleted(like.getIsDeleted())
        .build();
  }

  public List<LikeResponse> toLikeResponseList(List<Like> likeList) {
    return likeList.stream()
        .map(this::toLikeResponse)
        .toList();
  }
}
