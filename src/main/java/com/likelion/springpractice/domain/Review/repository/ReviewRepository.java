package com.likelion.springpractice.domain.Review.repository;

import com.likelion.springpractice.domain.Review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  boolean existsByUserIdAndFoodIdAndIsDeletedFalse(Long userId, Long foodId);

  List<Review> findAllByUserIdAndIsDeletedFalse(Long userId);

  Long countByUserId(Long userId);

  @Query("SELECT AVG(r.spicyLevel) "
      + "FROM Review r "
      + "WHERE r.food.id = :foodId AND r.isDeleted = false")
  Double calculateSpicyAverageForFood(@Param("foodId") Long foodId);
}
