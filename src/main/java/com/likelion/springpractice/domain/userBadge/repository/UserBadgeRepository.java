package com.likelion.springpractice.domain.userBadge.repository;

import com.likelion.springpractice.domain.userBadge.entity.UserBadge;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {

  List<UserBadge> findByUserIdOrderByCreatedAtDesc(Long userId);

  Optional<UserBadge> findByUserIdAndBadgeId(Long userId, Long badgeId);
}
