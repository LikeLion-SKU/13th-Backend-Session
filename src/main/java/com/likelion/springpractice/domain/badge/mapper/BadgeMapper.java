package com.likelion.springpractice.domain.badge.mapper;


import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.dto.response.OwnedBadgeResponse;
import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.own.entity.Own;
import org.springframework.stereotype.Component;

@Component
public class BadgeMapper {

  public BadgeResponse toBadgeResponse(Badge badge) {
    return BadgeResponse.builder()
        .badgeId(badge.getId())
        .badgeName(badge.getName())
        .badgeInformation(badge.getInformation())
        .count(badge.getCount())
        .build();
  }

  public OwnedBadgeResponse toOwnedBadgeResponse(Badge badge, boolean state) {
    return OwnedBadgeResponse.builder()
        .badgeId(badge.getId())
        .badgeName(badge.getName())
        .badgeInformation(badge.getInformation())
        .count(badge.getCount())
        .state(state)
        .build();
  }


}
