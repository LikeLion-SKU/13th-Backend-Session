package com.likelion.springpractice.domain.review.repository;

import com.likelion.springpractice.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByFoodId(Long foodId);
}
