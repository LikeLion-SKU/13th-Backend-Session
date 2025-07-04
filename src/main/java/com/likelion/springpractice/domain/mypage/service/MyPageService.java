package com.likelion.springpractice.domain.mypage.service;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.entity.UserBadge;
import com.likelion.springpractice.domain.badge.mapper.BadgeMapper;
import com.likelion.springpractice.domain.badge.repository.UserBadgeRepository;
import com.likelion.springpractice.domain.favoritefood.entity.FavoriteFood;
import com.likelion.springpractice.domain.favoritefood.repository.FavoriteFoodRepository;
import com.likelion.springpractice.domain.mypage.dto.response.*;
import com.likelion.springpractice.domain.mypage.mapper.MyPageMapper;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyPageService {

  private final UserRepository userRepository;
  private final MyPageMapper myPageMapper;
  private final FavoriteFoodRepository favoriteFoodRepository;
  private final ReviewRepository reviewRepository;
  private final UserBadgeRepository userBadgeRepository;
  private final BadgeMapper badgeMapper;

  public MyPageResponse getMyPage(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    log.info("MyPage retrieved successfully for userId={}", userId);
    return myPageMapper.toResponse(user);
  }

  public List<FavoriteFoodResponse> getFavorites(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    List<FavoriteFood> favorites = favoriteFoodRepository.findAllByUser(user);
    log.info("Favorite foods retrieved: userId={}, count={}", userId, favorites.size());

    return myPageMapper.toFavoriteFoodResponseList(favorites);
  }

  public List<ReviewResponse> getReviews(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    List<Review> reviews = reviewRepository.findAllByUser(user);
    log.info("Reviews retrieved: userId={}, count={}", userId, reviews.size());

    return myPageMapper.toReviewResponseList(reviews);
  }

  public List<BadgeResponse> getUserBadges(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    List<UserBadge> userBadges = userBadgeRepository.findAllByUser(user);
    log.info("Badges retrieved: userId={}, badgeCount={}", userId, userBadges.size());

    return badgeMapper.toBadgeResponseList(userBadges);
  }
}