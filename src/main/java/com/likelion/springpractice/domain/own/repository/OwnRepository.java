package com.likelion.springpractice.domain.own.repository;

import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.own.entity.Own;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnRepository extends JpaRepository<Own, Long> {
  List<Own> findAllByUserAndStateIsTrue(User user);
  List<Own> findAllByUser(User user);
  Optional<Own> findByBadge(Badge badge);
  Optional<Own> findByUserAndBadge(User user, Badge badge);
}
