package com.likelion.springpractice.domain.review.repository;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByFood(Food food);

  List<Review> findByUser(User user);

  
}
