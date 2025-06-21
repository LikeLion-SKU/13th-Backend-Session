package com.likelion.springpractice.domain.like.repository;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
  Optional<Like> findByUserAndFood(User user, Food food);
  int countByFoodAndStatusTrue(Food food); // 특정 음식의 좋아요 수 반환 (status가 true인 경우)
}
