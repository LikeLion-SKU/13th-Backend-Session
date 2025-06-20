package com.likelion.springpractice.domain.user.service;

import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.entity.Role;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.mapper.UserMapper;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public SignUpResponse signUp(SignUpRequest request) {
    System.out.println(request.getUsername() + ", " + request.getPassword());
    if(userRepository.existsByUsername(request.getUsername())) {
      throw new CustomException(UserErrorCode.USERNAME_ALREADY_EXISTS);
    }
    if(userRepository.existsByname(request.getName())) {
      throw new CustomException(UserErrorCode.NICKNAME_ALREADY_EXISTS);
    }

    // 비밀번호 인코딩
    String encodePassword = passwordEncoder.encode(request.getPassword());

    // 유저 엔티티 생성
    User user = User.builder()
        .username(request.getUsername())
        .name(request.getName())
        .password(encodePassword)
        .build();

    // 회원가입 성공 시 사용자의 역할을 바꿈
    user.role = Role.MEMBER;

    // 저장 및 로깅
    User savedUser = userRepository.save(user);

    // System.out.println("savedUser.getName() = " + savedUser.getName());

    log.info("New user registered: " + savedUser.getUsername()
        + " New user Nickname: " + savedUser.getName());

    return userMapper.tosignUpResponse(savedUser);
  }
}
