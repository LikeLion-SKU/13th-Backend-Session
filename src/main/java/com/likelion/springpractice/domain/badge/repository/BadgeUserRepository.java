package com.likelion.springpractice.domain.badge.repository;

import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.entity.BadgeUser;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeUserRepository extends JpaRepository<BadgeUser, Long> {

  boolean existsByUserAndBadge(User user, Badge badge);

  boolean deleteByUserAndBadge(User user, Badge badge);

  List<BadgeUser> findAllByUser(User user);
}
