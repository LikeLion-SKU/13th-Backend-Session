package com.likelion.springpractice.domain.user.service;

import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateEmailRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateLanguageRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdatePasswordRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateUsernameRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdateEmailResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdateLanguageResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdatePasswordResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdateUsernameResponse;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.mapper.UserMapper;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Schema
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  // 회원가입
  public SignUpResponse signUp(SignUpRequest request) {

    // 이메일 중복 검사
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new CustomException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }

    // 비밀번호 인코딩
    String encodedPassword = passwordEncoder.encode(request.getPassword());

    // 유저 엔티티 생성
    User user = User.builder()
        .email(request.getEmail())
        .password(encodedPassword)
        .username(request.getUsername())
        .language(request.getLanguage())
        .bio(request.getBio())
        .password(encodedPassword)
        .build();

    // 저장 및 로깅
    User savedUser = userRepository.save(user);
    log.info("New user registered: {}", savedUser.getUsername());

    return userMapper.toSignUpResponse(savedUser);
  }

  // 이메일 (아이디) 변경
  public UpdateEmailResponse updateEmail(Long userId, UpdateEmailRequest request) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    if (!user.getEmail().equals(request.getEmail()) &&
        userRepository.existsByEmail(request.getEmail())) {
      log.warn("Update email failed: Email already in use - newEmail={}", request.getEmail());
      throw new CustomException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }

    user.updateEmail(request.getEmail());
    log.info("Email updated: userId={}, newEmail={}", userId, user.getEmail());

    return UpdateEmailResponse.builder()
        .username(user.getUsername())
        .newEmail(user.getEmail())
        .build();
  }

  // 비밀번호 변경
  @Transactional
  public UpdatePasswordResponse updatePassword(Long userId, UpdatePasswordRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
      throw new CustomException(UserErrorCode.INVALID_PASSWORD);
    }

    user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
    log.info("Password updated successfully: userId={}", userId);

    return UpdatePasswordResponse.builder()
        .username(user.getUsername())
        .updated(true)
        .build();
  }

  // 이름(별명) 변경
  @Transactional
  public UpdateUsernameResponse updateUsername(Long userId, UpdateUsernameRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    user.updateUsername(request.getUsername());
    log.info("Username updated: userId={}, newUsername={}", userId, user.getUsername());

    return UpdateUsernameResponse.builder()
        .username(user.getUsername())
        .build();
  }

  // 언어 변경
  @Transactional
  public UpdateLanguageResponse updateLanguage(Long userId, UpdateLanguageRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    user.updateLanguage(request.getLanguage());
    log.info("Language updated: userId={}, newLanguage={}", userId, user.getLanguage());

    return UpdateLanguageResponse.builder()
        .username(user.getUsername())
        .language(user.getLanguage())
        .build();
  }
}
