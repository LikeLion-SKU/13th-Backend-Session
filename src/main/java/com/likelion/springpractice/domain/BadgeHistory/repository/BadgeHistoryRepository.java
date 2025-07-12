package com.likelion.springpractice.domain.BadgeHistory.repository;

import com.likelion.springpractice.domain.BadgeHistory.entity.BadgeHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeHistoryRepository extends JpaRepository<BadgeHistory, Long> {

  List<BadgeHistory> findByUserIdAndIsRevokedFalse(Long userId);

  Boolean existsByUserIdAndBadgeId(Long userId, Long badgeId);
}
