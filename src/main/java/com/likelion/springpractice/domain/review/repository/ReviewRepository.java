package com.likelion.springpractice.domain.review.repository;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByFoodId(Long foodId);

  //리뷰가 여러개라도, 음식은 중복되면 안되므로 distinct 사용!!
  @Query("SELECT DISTINCT r.food FROM Review r WHERE r.user.id = :userId")
  List<Food> findDistinctFoodsByUserId(@Param("userId") Long userId);
}
