package com.likelion.springpractice.domain.like.repository;

import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.food.entity.Food;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

  Optional<Like> findByUserAndFood(User user, Food food);

  List<Like> findAllByUserAndStateIsTrue(User user);
  boolean existsByUserAndFood(User user, Food food);
}
