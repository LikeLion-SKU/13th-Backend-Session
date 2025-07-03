package com.likelion.springpractice.domain.badge.repository;

import com.likelion.springpractice.domain.badge.entity.UserBadge;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
  List<UserBadge> findAllByUser(User user);
}
