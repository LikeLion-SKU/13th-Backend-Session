package com.likelion.springpractice.domain.badge.mapper;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.entity.Badge;
import org.springframework.stereotype.Component;

@Component
public class BadgeMapper {

  public BadgeResponse toResponse(Badge badge) {
    return BadgeResponse.builder()
        .name(badge.getName())
        .description(badge.getDescription())
        .build();
  }
}
