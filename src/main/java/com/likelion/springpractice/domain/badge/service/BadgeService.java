package com.likelion.springpractice.domain.badge.service;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.dto.response.BadgeUserResponse;
import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.entity.BadgeUser;
import com.likelion.springpractice.domain.badge.exception.BadgeErrorCode;
import com.likelion.springpractice.domain.badge.mapper.BadgeMapper;
import com.likelion.springpractice.domain.badge.repository.BadgeRepository;
import com.likelion.springpractice.domain.badge.repository.BadgeUserRepository;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.exception.CustomException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BadgeService {

  private final BadgeRepository badgeRepository;
  private final BadgeUserRepository badgeUserRepository;
  private final BadgeMapper badgeMapper;
  private final ReviewRepository reviewRepository;

  // 후기 개수가 바뀜에 따라서 배찌 목록 수정
  @Transactional
  public List<BadgeResponse> updateBadgesByUserFromReviewCount(User user) {
    long reviewCount = reviewRepository.countByUser(user);
    log.info("[BadgeService] 사용자 후기 개수:{}", reviewCount);
    List<Badge> badgeList = badgeRepository.findAll();

    for (Badge badge : badgeList) {
      String badgeName = badge.getBadgeName();
      boolean inRange = isInReviewRange(badge.getMinReview(), badge.getMaxReview(), reviewCount);

      //boolean inRange = isInReviewRange(badgeName, reviewCount);
      boolean alreadyBadge = badgeUserRepository.existsByUserAndBadge(user, badge);

      // 후가 개수 범위 조건은 만족하는데 그 배찌를 가지고 있지 않을때 배찌 생성하기 위함
      if (inRange && !alreadyBadge) {
        BadgeUser newBadgeUser = BadgeUser.builder()
            .user(user)
            .badge(badge)
            .build();
        badgeUserRepository.save(newBadgeUser);
        log.info("[BadgeService] 사용자별 배찌 생성 : userId={}, badgeName={}", user.getId(), badgeName);
      }
      // 조건에 만족하지 않은데 배찌를 가지고 있으면 삭제 -> 후기 5개->4개 되면 아기배찌 뺏어야함
      else if (alreadyBadge && !inRange) {
        badgeUserRepository.deleteByUserAndBadge(user, badge);
        log.info("[BadgeService] 사용자별 배찌 삭제 : userId={}, badgeName={}", user.getId(), badgeName);
      }
    }
    List<BadgeUser> newBadgeUserList = badgeUserRepository.findAllByUser(user);
    return newBadgeUserList.stream()
        .map(badgeUser -> badgeMapper.toBadgeResponse(badgeUser.getBadge())).toList();
  }

  // 사용자별 배찌 조회
  @Transactional
  public List<BadgeUserResponse> getBadgesByUser(User user) {
    log.info("[BadgeService] 사용자별 배찌 조회 시도");
    List<BadgeUser> badgeList = badgeUserRepository.findAllByUser(user);

    if (badgeList.isEmpty()) {
      log.warn("[BadgeService] 배찌 데이터가 존재하지 않음");
      throw new CustomException(BadgeErrorCode.BADGE__NOT_FOUND);
    }
    return badgeList.stream().map(
        badgeMapper::toBadgeUserResponse).toList();
  }

  // 후기 개수별 배찌 반환 조건 만족 여부
  public boolean isInReviewRange(int minReview, int maxReview, long count) {
    return count >= minReview && count < maxReview;
  }
  /*
  public boolean isInReviewRange(String badgeName, long count) {
    return switch (badgeName) {
      case "아기배찌" -> count >= 5 && count < 15;
      case "숟가락배찌" -> count >= 15 && count < 30;
      case "수저세트배찌" -> count >= 30 && count < 50;
      case "서버아이콘배찌" -> count >= 50;
      default -> false;
    };
  }
*/
}
