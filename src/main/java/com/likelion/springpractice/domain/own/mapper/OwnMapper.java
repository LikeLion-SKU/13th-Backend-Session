package com.likelion.springpractice.domain.own.mapper;


import com.likelion.springpractice.domain.badge.mapper.BadgeMapper;
import com.likelion.springpractice.domain.own.dto.response.OwnResponse;
import com.likelion.springpractice.domain.own.entity.Own;
import org.springframework.stereotype.Component;

@Component
public class OwnMapper {

  private final BadgeMapper badgeMapper;

  public OwnMapper(BadgeMapper badgeMapper) {
    this.badgeMapper = badgeMapper;
  }

  public OwnResponse toOwnResponse(Own own) {
    return OwnResponse.builder()
        .badge(badgeMapper.toBadgeResponse(own.getBadge()))
        .state(own.isState())
        .build();
  }


}
