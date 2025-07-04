package com.likelion.springpractice.week11.repository;

import com.likelion.springpractice.week11.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}