package com.likelion.springpractice.domain.Like.mapper;

import com.likelion.springpractice.domain.Food.entity.Food;
import com.likelion.springpractice.domain.Like.dto.response.LikeFoodResponse;
import com.likelion.springpractice.domain.Like.entity.Like;
import com.likelion.springpractice.domain.Review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeMapper {

  private final ReviewRepository reviewRepository;

  public LikeFoodResponse toResponse(Like like) {
    Food food = like.getFood();

    double averageSpice = reviewRepository.findByFood(food).stream()
        .mapToDouble(r -> r.getSpiceRating())
        .average()
        .orElse(0.0);

    return LikeFoodResponse.builder()
        .foodId(food.getId())
        .foodName(food.getName())
        .averageSpice(averageSpice)
        .build();
  }
}
