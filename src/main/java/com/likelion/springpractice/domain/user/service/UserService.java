package com.likelion.springpractice.domain.user.service;

import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateUserRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.mapper.UserMapper;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  // 회원가입
  @Transactional
  public SignUpResponse signUp(SignUpRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new CustomException(UserErrorCode.EMAIL_ALREADY_EXISTS);  // 수정함
    }

    //비밀번호 인코딩
    String encodePassword = passwordEncoder.encode(request.getPassword());

    //유저 엔티티 생성 -> Role 이랑 comment 왜 안넣었더라? -> 생각해보니 SignUpRequest에 일부러 안넣음
    User user = User.builder()
        .email(request.getEmail())
        .password(encodePassword)
        .username(request.getUsername())
        .nationality(request.getNationality())
        .build();

    //저장 및 로깅
    User savedUser = userRepository.save(user);
    log.info("New user registered: {}", savedUser.getUsername());

    return userMapper.toSignUpResponse(savedUser);
  }

  // 회원 정보 수정
  public User updateUser(User user, UpdateUserRequest request) {
    String encodedPassword = passwordEncoder.encode(request.getPassword());
    user.updateInfo(request.getName(), encodedPassword, request.getNationality());
    return userRepository.save(user);
  }


}
