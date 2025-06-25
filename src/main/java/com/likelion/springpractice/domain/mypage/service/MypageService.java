package com.likelion.springpractice.domain.mypage.service;

import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.like.repository.LikeRepository;
import com.likelion.springpractice.domain.mypage.dto.response.MypageLikesResponse;
import com.likelion.springpractice.domain.mypage.dto.response.MypageReviewsResponse;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {

  private final UserRepository userRepository;

  // 특정 사용자의 좋아요 리스트 반환
  public List<MypageLikesResponse> getLikeFoods(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
   return user.getLikes().stream()
       .filter(like -> like.isStatus()) // status가 true인 경우
       .map(like -> MypageLikesResponse.builder()
           .foodId(like.getFood().getFoodId())
           .foodName(like.getFood().getFoodName())
           .description(like.getFood().getDescription())
           .build())
       .toList();
  }

  // 특정 사용자의 리뷰 반환
  public List<MypageReviewsResponse> getReviewFoods(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    return user.getReviews().stream()
        .map(review -> MypageReviewsResponse.builder()
            .foodId(review.getFood().getFoodId())
            .foodName(review.getFood().getFoodName())
            .content(review.getContent())
            .build())
        .toList();
  }

}
