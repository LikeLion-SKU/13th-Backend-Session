package com.likelion.springpractice.domain.review.repository;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  // 사용자에 따른 후기 조회 -> for 마이페이지 후기 리스트
  List<Review> findAllByUser(User user);

  // 음식에 따른 후기 조회 -> for 음식 상세페이지의 후기 리스트
  List<Review> findAllByFood(Food food);

  // 사용자에 따른 후기 개수 조회 -> for 먹짱 배찌 지급
  Long countByUser(User user);
}
