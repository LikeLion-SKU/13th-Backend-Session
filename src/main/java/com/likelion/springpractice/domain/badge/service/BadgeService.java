package com.likelion.springpractice.domain.badge.service;

import com.likelion.springpractice.domain.Review.repository.ReviewRepository;
import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.entity.UserBadge;
import com.likelion.springpractice.domain.badge.mapper.BadgeMapper;
import com.likelion.springpractice.domain.badge.repository.BadgeRepository;
import com.likelion.springpractice.domain.badge.repository.UserBadgeRepository;
import com.likelion.springpractice.domain.user.entity.User;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadgeService {

  private final BadgeRepository badgeRepository;
  private final UserBadgeRepository userBadgeRepository;
  private final ReviewRepository reviewRepository;
  private final BadgeMapper badgeMapper;

  public List<BadgeResponse> getMyBadges(User user) {
    return userBadgeRepository.findByUser(user).stream()
        .map(userBadge -> badgeMapper.toResponse(userBadge.getBadge()))
        .collect(Collectors.toList());
  }

  public void checkAndAssignBadge(User user) {
    long reviewCount = reviewRepository.countByUser(user);

    if (reviewCount >= 50) {
      assign(user, "서버아이콘배찌");
    } else if (reviewCount >= 30) {
      assign(user, "수저세트배찌");
    } else if (reviewCount >= 15) {
      assign(user, "숟가락배찌");
    } else if (reviewCount >= 5) {
      assign(user, "아기배찌");
    }
  }

  public void recalculateBadges(User user) {
    long reviewCount = reviewRepository.countByUser(user);

    List<String> neededBadges;

    if (reviewCount >= 50) {
      neededBadges = List.of("아기배찌", "숟가락배찌", "수저세트배찌", "서버아이콘배찌");
    } else if (reviewCount >= 30) {
      neededBadges = List.of("아기배찌", "숟가락배찌", "수저세트배찌");
    } else if (reviewCount >= 15) {
      neededBadges = List.of("아기배찌", "숟가락배찌");
    } else if (reviewCount >= 5) {
      neededBadges = List.of("아기배찌");
    } else {
      neededBadges = List.of();
    }

    // 기존 보유한 뱃지 중 불필요한 건 제거
    List<UserBadge> currentBadges = userBadgeRepository.findByUser(user);
    for (UserBadge userBadge : currentBadges) {
      String badgeName = userBadge.getBadge().getName();
      if (!neededBadges.contains(badgeName)) {
        userBadgeRepository.delete(userBadge);
      }
    }

    // 필요한 뱃지 중 아직 없는 건 추가 지급
    for (String badgeName : neededBadges) {
      Badge badge = badgeRepository.findByName(badgeName)
          .orElseThrow(() -> new IllegalArgumentException("뱃지 정보가 없습니다: " + badgeName));

      boolean alreadyHas = userBadgeRepository.existsByUserAndBadge(user, badge);
      if (!alreadyHas) {
        userBadgeRepository.save(UserBadge.builder()
            .user(user)
            .badge(badge)
            .obtain(true)
            .acquiredAt(LocalDate.now())
            .build());
      }
    }
  }

  private void assign(User user, String badgeName) {
    Badge badge = badgeRepository.findByName(badgeName)
        .orElseThrow(() -> new IllegalArgumentException("뱃지 정보가 없습니다: " + badgeName));

    boolean alreadyHas = userBadgeRepository.existsByUserAndBadge(user, badge);
    if (!alreadyHas) {
      userBadgeRepository.save(UserBadge.builder()
          .user(user)
          .badge(badge)
          .obtain(true)
          .acquiredAt(LocalDate.now())
          .build());
    }
  }
}
