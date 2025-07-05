package com.likelion.springpractice.domain.mission.repository;

import com.likelion.springpractice.domain.mission.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  // LEFT JOIN FETCH는 User와 연결된 reviews를 한 번에 불러오도록 함.
  @Query("SELECT u FROM User u LEFT JOIN FETCH u.reviews WHERE u.id = :userId")
  Optional<User> findByIdWithReviews(@Param("userId") Long userId);

  @Query("SELECT u FROM User u " +
      "LEFT JOIN FETCH u.reviews r " +
      "LEFT JOIN FETCH u.grades g " +
      "WHERE u.id = :id")
  Optional<User> findByIdWithReviewsAndGrades(@Param("id") Long id); // ✅ 새로 추가

  @Query("SELECT u FROM User u LEFT JOIN FETCH u.grades WHERE u.id = :id")
  Optional<User> findByIdWithGrades(@Param("id") Long id);

}
