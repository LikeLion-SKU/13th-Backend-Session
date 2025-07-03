package com.likelion.springpractice.domain.Like.repository;

import com.likelion.springpractice.domain.Food.entity.Food;
import com.likelion.springpractice.domain.Like.entity.Like;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

  boolean existsByUserAndFood(User user, Food food);

  long countByFood(Food food);

  Optional<Like> findByUserAndFood(User user, Food food);

  List<Like> findByUser(User user);
}
