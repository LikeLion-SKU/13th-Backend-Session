package com.likelion.springpractice.domain.user.service;

import com.likelion.springpractice.domain.user.dto.request.PasswordUpdateRequest;
import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
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

  private final UserRepository userRepository;  // 사용자 DB 접근 객체
  private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 인코더
  private final UserMapper userMapper; // User → SignUpResponse 변환 매퍼

  @Transactional
  public SignUpResponse signUp(SignUpRequest request) {  // 회원가입 처리 메서드

    // 이미 존재하는 username인지 확인!!
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new CustomException(UserErrorCode.USERNAME_ALREADY_EXISTS);
    }

    // 비밀번호 암호화
    String encodedPassword = passwordEncoder.encode(request.getPassword());

    // 유저 엔티티 생성
    User user = User.builder()
        .username(request.getUsername())
        .password(encodedPassword)
        .nickname(request.getNickname())   // 필수
        .nation(request.getNation())      // 필수 (enum)
        .introduce(request.getIntroduce()) // 선택
        .build();

    // 저장 및 로깅
    User savedUser = userRepository.save(user);
    log.info("New user registered: {}", savedUser.getUsername());

    // 응답 DTO로 변환 후 반환
    return userMapper.toSignUpResponse(savedUser);
  }


  //이 부분 추가했지만, 잘 돌아가지 않아, 우선 모든 기능들 구현 후, 로그인 정보 붙이기로함!!
  //+ PasswordUpdateRequest + UserErrorCode + UserController 내용 추가했음
  @Transactional
  public void updatePassword(Long userId, PasswordUpdateRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 현재 비밀번호 일치 여부 확인
    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
      throw new CustomException(UserErrorCode.INVALID_PASSWORD);
    }

    // 새 비밀번호로 변경
    user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);
  }
}
