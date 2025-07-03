package com.likelion.springpractice.domain.user.service;

import com.likelion.springpractice.domain.Like.dto.response.LikeFoodResponse;
import com.likelion.springpractice.domain.Like.mapper.LikeMapper;
import com.likelion.springpractice.domain.Like.repository.LikeRepository;
import com.likelion.springpractice.domain.Review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.Review.mapper.ReviewMapper;
import com.likelion.springpractice.domain.Review.repository.ReviewRepository;
import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.mapper.BadgeMapper;
import com.likelion.springpractice.domain.badge.repository.UserBadgeRepository;
import com.likelion.springpractice.domain.user.dto.response.MyPageResponse;
import com.likelion.springpractice.domain.user.dto.response.MyPageUserResponse;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

  private final ReviewRepository reviewRepository;
  private final LikeRepository likeRepository;
  private final UserBadgeRepository userBadgeRepository;

  private final ReviewMapper reviewMapper;
  private final LikeMapper likeMapper;
  private final BadgeMapper badgeMapper;

  public MyPageUserResponse getMyInfo(User user) {
    return MyPageUserResponse.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .national(user.getNational())
        .introduction(user.getIntroduction())
        .build();
  }

  public List<ReviewResponse> getMyReviews(User user) {
    return reviewRepository.findByUser(user).stream()
        .map(reviewMapper::toResponse)
        .collect(Collectors.toList());
  }

  public List<LikeFoodResponse> getMyLikes(User user) {
    return likeRepository.findByUser(user).stream()
        .map(likeMapper::toResponse)
        .collect(Collectors.toList());
  }

  public List<BadgeResponse> getMyBadges(User user) {
    return userBadgeRepository.findByUser(user).stream()
        .map(userBadge -> badgeMapper.toResponse(userBadge.getBadge()))
        .collect(Collectors.toList());
  }

  public MyPageResponse getMyPage(User user) {
    return MyPageResponse.builder()
        .userInfo(getMyInfo(user))
        .reviews(getMyReviews(user))
        .likes(getMyLikes(user))
        .badges(getMyBadges(user))
        .build();
  }
}
