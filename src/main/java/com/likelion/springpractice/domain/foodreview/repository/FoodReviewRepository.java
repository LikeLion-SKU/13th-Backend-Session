package com.likelion.springpractice.domain.foodreview.repository;

import com.likelion.springpractice.domain.foodreview.entity.FoodReview;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodReviewRepository extends JpaRepository<FoodReview, Long> {

    Optional<FoodReview> findByUserIdAndFoodId(Long userId, Long foodId);

    List<FoodReview> findAllByUserId(Long userId);

    Long countByUserId(Long userId);

    @Query("select avg(r.score) from FoodReview  r where r.food.id = :foodId")
    Float findAverageScoreByFoodId(@Param("foodId") Long foodId);

}
