package com.likelion.springpractice.domain.badge.mapper;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.entity.UserBadge;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BadgeMapper {

  public List<BadgeResponse> toBadgeResponseList(List<UserBadge> userBadges) {
    return userBadges.stream()
        .map(userBadge -> BadgeResponse.builder()
            .name(userBadge.getBadge().getBadgeName())
            .description(userBadge.getBadge().getDescription())
            .build())
        .collect(Collectors.toList());
  }
}