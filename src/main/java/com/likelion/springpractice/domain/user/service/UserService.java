package com.likelion.springpractice.domain.user.service;

import com.likelion.springpractice.domain.post.exception.PostErrorCode;
import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.post.week05.dto.response.PostResponse;
import com.likelion.springpractice.domain.user.dto.request.ChangePasswordRequest;
import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdateResponse;
import com.likelion.springpractice.domain.user.dto.response.UserResponse;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.mapper.UserMapper;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import com.likelion.springpractice.global.exception.GlobalErrorCode;
import com.likelion.springpractice.global.exception.GlobalExceptionHandler;
import java.util.ArrayList;
import java.util.List;
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

  @Transactional
  public SignUpResponse signUp(SignUpRequest request) {
    // 1. 입력된 아이디로 회원이 있는지 확인. 있으면 에러
    if(userRepository.existsByUsername(request.getUsername())) {
      throw new CustomException(UserErrorCode.USERNAME_ALREADY_EXISTS);
    }

    // 2. 비번 암호화
    String encodedPassword = passwordEncoder.encode(request.getPassword());

    // 3. 암호화된 비번과 받은 정보로 엔티티를 만듦.
    User user = User.builder()
        .username(request.getUsername())
        .password(encodedPassword)
        .name(request.getName())
        .language(request.getLanguage())
        .introduce(request.getIntroduce())
        .build();

    // 4. User 엔티티로 레포지토리에 접근(저장)
    User savedUser = userRepository.save(user);
    log.info("New user registered: {}", savedUser.getUsername());

    return userMapper.toSignUpResponse(savedUser);
  }


  @Transactional
  public UpdateResponse updateUser(UpdateRequest request) {
    // 1. 로그인 된 아이디로 회원이 있는지 확인. 없으면 에러
    User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 2. 이미 있는 정보에서 requset로 준 것만 변경.
    List<String> changed = new ArrayList<>();

    if(request.getName() != null) {
      user.setName(request.getName());
      changed.add("name");
    }

    if(request.getLanguage() != null) {
      user.setLanguage(request.getLanguage());
      changed.add("language");
    }

    if(request.getIntroduce() != null) {
      user.setIntroduce(request.getIntroduce());
      changed.add("introduce");
    }

    // 3. 변경된 게 없으면 Bad Requset 에러
    if(changed.isEmpty()) {
      throw new CustomException(GlobalErrorCode.INVALID_JSON_FORMAT);
    }

    // 4. 변경된 것 로그 찍고, return문까지 가면 User 엔티티로 레포지토리에 접근(저장)
    log.info("user information({}) update: {}", String.join(", ", changed), user.getUsername());
    return userMapper.toUpdateResponse(user);
    
  }


  @Transactional
  public UpdateResponse updatePassword(ChangePasswordRequest request) {
    // 1. 로그인 된 아이디로 회원이 있는지 확인. 없으면 에러
    User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 2. 입력한 현재 비밀번호가 일치하는지 확인. 틀리면 에러
    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
      throw new CustomException(UserErrorCode.PASSWORD_NOT_MATCHED);
    }

    // 4. 변경된 것 로그 찍고, return문까지 가면 User 엔티티로 레포지토리에 접근(저장)
    log.info("user password Change: {}",  user.getUsername());
    return userMapper.toUpdateResponse(user);

  }


  // 회원 단일 조회
  @Transactional
  public UserResponse getUserById(String userName) {
    // 넘겨받은 아이디와 같은 회원 찾기. 없으면 에러
    User user = userRepository.findByUsername(userName).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 해당 회원의 정보를 리턴.
    return userMapper.toUserResponse(user);
  }


  // 회원 전체 조회
  @Transactional
  public List<UserResponse> getAllUsers() {
    List<User> userList = userRepository.findAll();
    return userList.stream().map(userMapper::toUserResponse).toList();
  }

}
