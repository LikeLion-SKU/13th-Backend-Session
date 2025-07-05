package com.likelion.springpractice.global.security;

import com.likelion.springpractice.domain.mission.entity.User;
import com.likelion.springpractice.domain.mission.exception.UserErrorCode;
import com.likelion.springpractice.domain.mission.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    log.info("✅ [UserDetailsService] DB에서 조회 시도한 이메일: {}", email);  // ✅ 추가
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    return new CustomUserDetails(user);
  }
}
