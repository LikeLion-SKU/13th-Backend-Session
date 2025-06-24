package com.likelion.springpractice.domain.badge.repository;

import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.entity.BadgeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {


  Badge findByBadgeName(BadgeName badgeName);
}
