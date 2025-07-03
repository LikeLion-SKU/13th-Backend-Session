package com.likelion.springpractice.domain.badge.mapper;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.dto.response.BadgeUserResponse;
import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.entity.BadgeUser;
import org.springframework.stereotype.Component;

@Component
public class BadgeMapper {

  public BadgeResponse toBadgeResponse(Badge badge) {

    return BadgeResponse.builder()
        .badgeId(badge.getBadgeId())
        .badgeName(badge.getBadgeName())
        .minReview(badge.getMinReview())
        .maxReview(badge.getMaxReview())
        .build();
  }

  public BadgeUserResponse toBadgeUserResponse(BadgeUser badgeUser) {

    return BadgeUserResponse.builder()
        .userId(badgeUser.getUser().getId())
        .badgeId(badgeUser.getBadge().getBadgeId())
        .badgeName(badgeUser.getBadge().getBadgeName())
        .build();
  }
}
