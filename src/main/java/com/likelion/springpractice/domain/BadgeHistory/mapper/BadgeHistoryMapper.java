package com.likelion.springpractice.domain.BadgeHistory.mapper;

import com.likelion.springpractice.domain.BadgeHistory.dto.response.BadgeHistoryResponse;
import com.likelion.springpractice.domain.BadgeHistory.entity.BadgeHistory;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BadgeHistoryMapper {

  public List<BadgeHistoryResponse> toBadgeHistoryResponseList(List<BadgeHistory> badgeHistories) {

    return badgeHistories.stream()
        .map(badgeHistory -> BadgeHistoryResponse.builder()
            .badgeHistoryId(badgeHistory.getId())
            .badgeName(badgeHistory.getBadge().getName())
            .badgeDescription(badgeHistory.getBadge().getDescription())
            .isRevoked(badgeHistory.getIsRevoked())
            .build())
        .toList();
  }

}
