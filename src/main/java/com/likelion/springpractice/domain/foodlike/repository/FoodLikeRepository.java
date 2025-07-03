package com.likelion.springpractice.domain.foodlike.repository;

import com.likelion.springpractice.domain.foodlike.entity.FoodLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodLikeRepository extends JpaRepository<FoodLike, Long> {

    Optional<FoodLike> findByUserIdAndFoodId(Long userId, Long foodId);
}
