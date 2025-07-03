package com.likelion.springpractice.domain.review.repository;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByUser(User user);
  List<Review> findByFood(Food food);
  Optional<Review> findByUserAndFood(User user, Food food);
  Long countByUser(User user);
  boolean existsByUserAndFood(User user, Food food);

  // 1. 후기 개수
  @Query("SELECT COUNT(r) FROM Review r WHERE r.food.id = :foodId")
  Long countByFoodId(@Param("foodId") Long foodId);

  // 2. 평점 총합
  @Query("SELECT SUM(r.rate) FROM Review r WHERE r.food.id = :foodId")
  Double sumRateByFoodId(@Param("foodId") Long foodId);


}
