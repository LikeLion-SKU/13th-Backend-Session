package com.likelion.springpractice.domain.user.repository;

import com.likelion.springpractice.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  // username으로 사용자 조회
  Optional<User> findByUsername(String username);

  // username 존재 여부 확인 (중복 체크용)
  boolean existsByUsername(String username);
}
