package com.likelion.springpractice.domain.Badge.mapper;

import com.likelion.springpractice.domain.Badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.Badge.entity.Badge;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BadgeMapper {

  public BadgeResponse toBadgeResponse(Badge badge) {
    return BadgeResponse.builder()
        .badgeId(badge.getId())
        .badgeName(badge.getName())
        .description(badge.getDescription())
        .unlock_count(badge.getUnlockCount())
        .build();
  }

  public List<BadgeResponse> toBadgeResponseList(List<Badge> badges) {
    return badges.stream()
        .map(this::toBadgeResponse)
        .toList();
  }
}
