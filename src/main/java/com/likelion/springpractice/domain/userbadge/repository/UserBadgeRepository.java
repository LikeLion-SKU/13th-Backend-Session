package com.likelion.springpractice.domain.userbadge.repository;

import com.likelion.springpractice.domain.userbadge.entity.UserBadge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {

    List<UserBadge> findAllByUserId(Long userId);

    Boolean existsByUserIdAndBadgeId(Long userId, Long badgeId);
}
