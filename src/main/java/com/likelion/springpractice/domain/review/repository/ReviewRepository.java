package com.likelion.springpractice.domain.review.repository;

import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findAllByUser(User user);
  int countByUser(User user);
}