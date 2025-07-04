package com.likelion.springpractice.week11.repository;

import com.likelion.springpractice.week11.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByFoodId(Long foodId);
    List<Review> findAllByUserId(Long userId);
}