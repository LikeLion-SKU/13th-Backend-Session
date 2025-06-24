package com.likelion.springpractice.domain.badge.service;

import com.likelion.springpractice.domain.MappingUserBadge.entity.MappingUserBadge;
import com.likelion.springpractice.domain.MappingUserBadge.repository.MappingUserBadgeRepository;
import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.entity.BadgeName;
import com.likelion.springpractice.domain.badge.repository.BadgeRepository;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadgeService {

  private final BadgeRepository badgeRepository;
  private final UserRepository userRepository;
  private final ReviewRepository reviewRepository;
  private final MappingUserBadgeRepository mappingUserBadgeRepository;

  // 사용자 배찌 지급
  @Transactional
  public void getBadge(String username) {

    // 사용자 존재 확인
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    int reviewCount = reviewRepository.countByUser(user); // 사용자가 작성한 리뷰 개수

    for (BadgeName badgeName : BadgeName.values()) {
      Badge badge = badgeRepository.findByBadgeName(badgeName); // 배찌 조회
      int requiredReview = badgeName.getRequiredReviews(); // 배찌 지급에 필요한 리뷰 개수
      int shouldHaveCount = reviewCount / requiredReview; // 사용자가 가지고 있어야 할 배찌 개수
      if (shouldHaveCount == 0) continue; // 리뷰 수 부족 → 배찌 지급 조건 미달 시 패스
      int alreadyHaveCount = mappingUserBadgeRepository.countByUserAndBadge(user, badge); // 사용자가 이미 가지고 있는 배찌 개수

      int toGiveCount = shouldHaveCount - alreadyHaveCount; // 최종적으로 사용자가 가지고 있어야 할 배찌 개수
      for (int i = 0; i < toGiveCount; i++) {
        MappingUserBadge mapping = MappingUserBadge.builder()
            .user(user)
            .badge(badge)
            .build();
        mappingUserBadgeRepository.save(mapping);
      }
    }
  }

}
