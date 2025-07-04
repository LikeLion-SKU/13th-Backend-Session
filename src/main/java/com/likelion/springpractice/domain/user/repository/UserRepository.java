package com.likelion.springpractice.domain.user.repository;

import com.likelion.springpractice.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  boolean existsByEmail(String email);
  Optional<User> findByEmail(String email);
}