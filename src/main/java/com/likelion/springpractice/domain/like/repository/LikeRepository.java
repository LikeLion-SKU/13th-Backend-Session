package com.likelion.springpractice.domain.like.repository;

import com.likelion.springpractice.domain.like.entity.FoodLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<FoodLike, Long> {
  Optional<FoodLike> findByFoodIdAndUserId(Long foodId, Long userId);
  int countByFoodIdAndIsLikedTrue(Long foodId);
  List<FoodLike> findByUserIdAndIsLikedTrue(Long userId);
}
