package com.likelion.springpractice.week11.repository;

import com.likelion.springpractice.week11.domain.FoodLike;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface FoodLikeRepository extends JpaRepository<FoodLike, Long> {
    Optional<FoodLike> findByUserIdAndFoodId(Long userId, Long foodId);
    List<FoodLike> findAllByUserId(Long userId);
}
