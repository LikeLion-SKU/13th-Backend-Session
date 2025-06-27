package com.likelion.springpractice.domain.review.repository;

import com.likelion.springpractice.domain.review.entity.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findByFoodId(Long foodId);
  List<Review> findByUserId(Long userId);
  Optional<Review> findByIdAndUserId(Long id, Long userId); // 작성자 검증용

  long countByUserId(Long userId);
}