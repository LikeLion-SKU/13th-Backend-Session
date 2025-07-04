package com.likelion.springpractice.domain.BadgeHistory.service;

import com.likelion.springpractice.domain.BadgeHistory.dto.response.BadgeHistoryResponse;
import com.likelion.springpractice.domain.BadgeHistory.entity.BadgeHistory;
import com.likelion.springpractice.domain.BadgeHistory.exception.BadgeHistoryErrorCode;
import com.likelion.springpractice.domain.BadgeHistory.mapper.BadgeHistoryMapper;
import com.likelion.springpractice.domain.BadgeHistory.repository.BadgeHistoryRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BadgeHistoryService {

  private final BadgeHistoryRepository badgeHistoryRepository;
  private final BadgeHistoryMapper badgeHistoryMapper;

  @Transactional(readOnly = true)
  public List<BadgeHistoryResponse> getAllBadges(Long userId) {

    if (userId == null) {
      throw new CustomException(BadgeHistoryErrorCode.BADGE_USER_UNAUTHORIZED);
    }

    List<BadgeHistory> badgeHistorieList = badgeHistoryRepository.findAllByUserId(userId);
    return badgeHistoryMapper.toBadgeHistoryResponseList(badgeHistorieList);
  }
}
