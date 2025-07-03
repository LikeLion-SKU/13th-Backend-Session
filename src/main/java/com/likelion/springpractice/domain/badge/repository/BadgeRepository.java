package com.likelion.springpractice.domain.badge.repository;

import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

  List<Badge> findAllByUser(User user);

  Boolean deleteAllByUser(User user);

  Boolean existsByUserAndBadgeId(User user, Long badgeId);
}
