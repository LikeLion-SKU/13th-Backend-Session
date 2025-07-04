package com.likelion.springpractice.domain.badge.service;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.entity.UserBadge;
import com.likelion.springpractice.domain.badge.enums.BadgeType;
import com.likelion.springpractice.domain.badge.exception.BadgeErrorCode;
import com.likelion.springpractice.domain.badge.repository.BadgeRepository;
import com.likelion.springpractice.domain.badge.repository.UserBadgeRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BadgeService {

  private final BadgeRepository badgeRepository;
  private final UserBadgeRepository userBadgeRepository;


  /**
   * 사용자가 보유한 뱃지 목록을 조회하는 서비스 메서드.
   * <p>
   * 주어진 {@link User} 객체로부터 해당 사용자가 보유한 뱃지들의 목록을 조회하고, 이를 {@link BadgeResponse} DTO 리스트로 변환하여 반환한다.
   * </p>
   *
   * @param user 조회할 사용자 정보가 담긴 {@link User} 객체
   * @return 사용자가 보유한 뱃지들의 목록이 담긴 {@link List<BadgeResponse>} 객체
   */
  public void checkAndAssignBadge(User user) {
    log.info("[서비스] 후기 개수 확인 후 뱃지 할당 시도");
    int reviewCount = user.getReviewCount();

    switch (reviewCount) {
      case 5:
        assign(user, BadgeType.valueOf("BABY_BADGE"));
        break;
      case 15:
        assign(user, BadgeType.valueOf("SPOON_BADGE"));
        break;
      case 30:
        assign(user, BadgeType.valueOf("SPOON_SET_BADGE"));
        break;
      case 50:
        assign(user, BadgeType.valueOf("ICON_BADGE"));
        break;
    }
  }

  /**
   * 사용자가 보유한 뱃지 목록을 조회하는 서비스 메서드.
   * <p>
   * 주어진 {@link User} 객체로부터 해당 사용자가 보유한 뱃지들의 목록을 조회하고, 이를 {@link BadgeResponse} DTO 리스트로 변환하여 반환한다.
   * </p>
   *
   * @param user 조회할 사용자 정보가 담긴 {@link User} 객체
   * @return 사용자가 보유한 뱃지들의 목록이 담긴 {@link List<BadgeResponse>} 객체
   */
  private void assign(User user, BadgeType badgeName) {
    Badge badge = badgeRepository.findByName(badgeName)
        .orElseThrow(() -> new CustomException(BadgeErrorCode.BADGE_ERROR_CODE));

    userBadgeRepository.save(UserBadge.builder()
        .badge(badge)
        .user(user)
        .build());

  }
}




