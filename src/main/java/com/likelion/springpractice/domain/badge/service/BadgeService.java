package com.likelion.springpractice.domain.badge.service;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.entity.BadgeName;
import com.likelion.springpractice.domain.badge.entity.mapping.MappingUserBadge;
import com.likelion.springpractice.domain.badge.mapper.BadgeMapper;
import com.likelion.springpractice.domain.badge.repository.BadgeRepository;
import com.likelion.springpractice.domain.badge.repository.MappingUserBadgeRepository;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BadgeService {

  private final BadgeRepository badgeRepository;
  private final MappingUserBadgeRepository mappingRepo;
  private final UserRepository userRepository;
  private final ReviewRepository reviewRepository;
  private final BadgeMapper badgeMapper;

  // 리뷰 작성 후 호출해서 자동 지급
  public void assignBadges(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 작성 리뷰 수
    long reviewCnt = reviewRepository.countByUserId(user.getId());

    for (BadgeName badgeName : BadgeName.values()) {
      Badge badge = badgeRepository.findByBadgeName(badgeName);
      if (badge == null) continue;

      int need = badgeName.getRequiredReviews();
      int shouldHave = (int) (reviewCnt / need); // 이론상 개수
      int already    = mappingRepo.countByUserAndBadge(user, badge);

      for (int i = 0; i < shouldHave - already; i++) {
        mappingRepo.save(
            MappingUserBadge.builder().user(user).badge(badge).build());
      }
    }
  }

  // 내 배지 목록 조회
  public List<BadgeResponse> getMyBadges(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    return badgeRepository.findAll().stream()
        .map(b -> badgeMapper.toResponse(
            b,
            mappingRepo.countByUserAndBadge(user, b)))
        .collect(Collectors.toList());
  }
}