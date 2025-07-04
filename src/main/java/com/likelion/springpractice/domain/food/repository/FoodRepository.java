package com.likelion.springpractice.domain.food.repository;

import com.likelion.springpractice.domain.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}