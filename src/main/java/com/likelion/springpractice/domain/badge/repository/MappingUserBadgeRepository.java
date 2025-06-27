package com.likelion.springpractice.domain.badge.repository;

import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.entity.mapping.MappingUserBadge;
import com.likelion.springpractice.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MappingUserBadgeRepository extends JpaRepository<MappingUserBadge, Long> {
  int countByUserAndBadge(User user, Badge badge);
}