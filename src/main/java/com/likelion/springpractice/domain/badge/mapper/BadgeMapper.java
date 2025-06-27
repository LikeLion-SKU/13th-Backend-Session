package com.likelion.springpractice.domain.badge.mapper;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.entity.Badge;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BadgeMapper {

  // 단일 변환
  public BadgeResponse toResponse(Badge badge, int ownedCount) {
    return BadgeResponse.builder()
        .badgeName(badge.getBadgeName())
        .requiredReviews(badge.getBadgeName().getRequiredReviews())
        .ownedCount(ownedCount)
        .build();
  }

  // 리스트 변환
  public List<BadgeResponse> toResponseList(List<Badge> list, int ownedCountPerBadge) {
    return list.stream()
        .map(b -> toResponse(b, ownedCountPerBadge))
        .collect(Collectors.toList());
  }
}