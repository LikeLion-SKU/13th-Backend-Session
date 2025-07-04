package com.likelion.springpractice.domain.mypage.mapper;

import com.likelion.springpractice.domain.favoritefood.entity.FavoriteFood;
import com.likelion.springpractice.domain.mypage.dto.response.*;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MyPageMapper {

  public MyPageResponse toResponse(User user) {

    return MyPageResponse.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .language(user.getLanguage())
        .bio(user.getBio())
        .build();
  }

  public List<FavoriteFoodResponse> toFavoriteFoodResponseList(List<FavoriteFood> favorites) {
    return favorites.stream()
        .map(favoriteFood -> FavoriteFoodResponse.builder()
            .foodId(favoriteFood.getId())
            .foodName(favoriteFood.getFood().getFoodName())
            .build())
        .collect(Collectors.toList());
  }

  public List<ReviewResponse> toReviewResponseList(List<Review> reviews) {
    return reviews.stream()
        .map(review -> ReviewResponse.builder()
            .reviewId(review.getId())
            .foodName(review.getFood().getFoodName())
            .spicyLevel(review.getSpicyLevel())
            .comment(review.getComment())
            .build())
        .collect(Collectors.toList());
  }
}