package com.likelion.springpractice.domain.mypage.service;


import com.likelion.springpractice.domain.badge.service.BadgeService;
import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.mapper.LikeMapper;
import com.likelion.springpractice.domain.like.repository.LikeRepository;
import com.likelion.springpractice.domain.mypage.dto.response.MyPageResponse;
import com.likelion.springpractice.domain.mypage.mapper.MyPageMapper;
import com.likelion.springpractice.domain.review.mapper.ReviewMapper;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

  private final BadgeService badgeService;
  private final LikeRepository likeRepository;
  private final ReviewRepository reviewRepository;
  private final LikeMapper likeMapper;
  private final ReviewMapper reviewMapper;
  private final MyPageMapper myPageMapper;

  public MyPageResponse getMyPage(long userId) {

    var badgeResponses = badgeService.getMyBadges(userId);

    var likes = likeRepository.findByUserIdAndIsLikedTrue(userId);
    List<LikeResponse> likeResponses = likes.stream()
        .map(like -> likeMapper.toResponse(
            like.getFood().getId(),
            likeRepository.countByFoodIdAndIsLikedTrue(like.getFood().getId()),
            like.isLiked()))
        .toList();

    var reviews = reviewRepository.findByUserId(userId);
    var reviewResponses = reviews.stream()
        .map(reviewMapper::toResponse)
        .toList();

    return myPageMapper.toResponse(badgeResponses, likeResponses, reviewResponses);
  }
}