package com.likelion.springpractice.domain.Badge.repository;

import com.likelion.springpractice.domain.Badge.entity.Badge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

  List<Badge> findByUnlockCount(Long reviewCount);
}
