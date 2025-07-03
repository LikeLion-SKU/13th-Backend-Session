package com.likelion.springpractice.domain.like.mapper;


import com.likelion.springpractice.domain.food.mapper.FoodMapper;
import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

  private final FoodMapper foodMapper;

  public LikeMapper(FoodMapper foodMapper) {
    this.foodMapper = foodMapper;
  }

  public LikeResponse toLikeResponse(Like like) {
    return LikeResponse.builder()
        .food(foodMapper.toFoodResponse(like.getFood()))
        .state(like.isState())
        .build();
  }
}
