package com.likelion.springpractice.domain.review.repository;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findByFood_FoodId(Long foodId); // 음식 ID로 리뷰 조회
  int countByUser(User user); // 특정 사용자가 작성한 리뷰 개수
  Optional<Review> findByUser_UserIdAndFood_FoodId(Long userId, Long foodId);
  boolean existsByUserAndFood(User user, Food food);

}
