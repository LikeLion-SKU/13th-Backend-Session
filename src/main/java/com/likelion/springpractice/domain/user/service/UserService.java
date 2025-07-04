package com.likelion.springpractice.domain.user.service;

import static com.likelion.springpractice.domain.user.entity.Role.USER;
import static com.likelion.springpractice.domain.user.entity.Status.ACTIVE;

import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.request.UserRequest.SelfIntroRequest;
import com.likelion.springpractice.domain.user.dto.request.UserRequest.SettingsRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.dto.response.UserResponse;
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
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  @Transactional
  public SignUpResponse signUp(SignUpRequest request) {

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new CustomException(UserErrorCode.USERNAME_ALREADY_EXISTS);
    }

    // 비밀번호 인코딩
    String encodedPassword = passwordEncoder.encode(request.getPassword());

    // 유저 엔티티 생성
    User user = User.builder()
        .email(request.getEmail())
        .nickname(request.getNickname())
        .password(encodedPassword)
        .nation(request.getNation())
        .selfIntro(request.getSelfIntro())
        .role(request.getRole() != null ? request.getRole() : USER)
        .status(ACTIVE)
        .build();

    // 저장 및 로깅
    User savedUser = userRepository.save(user);
    log.info("New user registered: {}", savedUser.getEmail());

    return userMapper.toSignUpResponse(savedUser);
  }

  @Transactional(readOnly = true)
  public UserResponse getUserInfoById(Long id) {

    User user = userRepository.findById(id)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    return userMapper.toUserResponse(user);
  }

  @Transactional
  public UserResponse updateSelfIntro(Long id, SelfIntroRequest request) {

    User user = userRepository.findById(id)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    user.updateSelfIntro(request.getSelfIntro());

    return userMapper.toUserResponse(user);
  }

  @Transactional
  public UserResponse updateSettings(Long id, SettingsRequest request) {

    User user = userRepository.findById(id)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    user.updateSettings(request.getNation(), request.getNickName());

    return userMapper.toUserResponse(user);
  }
}
