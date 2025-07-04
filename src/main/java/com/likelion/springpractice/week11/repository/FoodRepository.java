package com.likelion.springpractice.week11.repository;

import com.likelion.springpractice.week11.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}