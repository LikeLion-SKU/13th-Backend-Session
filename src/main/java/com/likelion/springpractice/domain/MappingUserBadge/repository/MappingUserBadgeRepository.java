package com.likelion.springpractice.domain.MappingUserBadge.repository;

import com.likelion.springpractice.domain.MappingUserBadge.entity.MappingUserBadge;
import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MappingUserBadgeRepository extends JpaRepository<MappingUserBadge, Long> {
  int countByUserAndBadge(User user, Badge badge);
  List<MappingUserBadge> findAllByUser(User user);

}
