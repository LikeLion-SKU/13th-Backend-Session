package com.likelion.springpractice.domain.badge.service;

import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.entity.UserBadge;
import com.likelion.springpractice.domain.badge.repository.BadgeRepository;
import com.likelion.springpractice.domain.badge.repository.UserBadgeRepository;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BadgeService {

  private final UserBadgeRepository userBadgeRepository;
  private final BadgeRepository badgeRepository;
  private final ReviewRepository reviewRepository;

  public void issueBadgesIfEligible(User user) {
    int reviewCount = reviewRepository.countByUser(user);
    log.info("Checking badge eligibility: userId={}, reviewCount={}", user.getId(), reviewCount);

    // 유저가 이미 받은 뱃지들
    Set<Long> alreadyIssuedBadgeIds = userBadgeRepository.findAllByUser(user).stream()
        .map(userBadge -> userBadge.getBadge().getId())
        .collect(Collectors.toSet());

    List<Badge> allBadges = badgeRepository.findAll();

    List<UserBadge> newBadges = allBadges.stream()
        .filter(badge -> reviewCount >= badge.getConditionReviewCount())
        .filter(badge -> !alreadyIssuedBadgeIds.contains(badge.getId()))
        .map(badge -> {
          log.info("Eligible for badge: badgeId={}, badgeName={}", badge.getId(), badge.getBadgeName());
          return UserBadge.builder()
              .user(user)
              .badge(badge)
              .build();
        })
        .toList();

    if (newBadges.isEmpty()) {
      log.info("No new badges to issue for userId={}", user.getId());
    } else {
      userBadgeRepository.saveAll(newBadges);
      log.info("Issued {} new badge(s) to userId={}", newBadges.size(), user.getId());
    }
  }
}