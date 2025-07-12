package com.likelion.springpractice.domain.Badge.service;

import com.likelion.springpractice.domain.Badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.Badge.entity.Badge;
import com.likelion.springpractice.domain.Badge.mapper.BadgeMapper;
import com.likelion.springpractice.domain.Badge.repository.BadgeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BadgeService {

  private final BadgeRepository badgeRepository;
  private final BadgeMapper badgeMapper;

  @Transactional(readOnly = true)
  public List<BadgeResponse> getAllBadges() {

    List<Badge> badgeList = badgeRepository.findAll();

    return badgeMapper.toBadgeResponseList(badgeList);
  }
}
