package com.likelion.springpractice.domain.badge.repository;

import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.enums.BadgeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BadgeRepository extends JpaRepository<Badge, Long> {

  Optional<Badge> findByName(BadgeType name);
}