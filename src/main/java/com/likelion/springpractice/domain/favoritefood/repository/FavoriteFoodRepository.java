package com.likelion.springpractice.domain.favoritefood.repository;

import com.likelion.springpractice.domain.favoritefood.entity.FavoriteFood;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  FavoriteFoodRepository extends JpaRepository<FavoriteFood, Long> {
  boolean existsByUserAndFood(User user, Food food);
  Optional<FavoriteFood> findByUserAndFood(User user, Food food);
  int countByFood(Food food);
  List<FavoriteFood> findAllByUser(User user);
}