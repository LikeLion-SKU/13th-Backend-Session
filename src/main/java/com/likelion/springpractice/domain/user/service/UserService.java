package com.likelion.springpractice.domain.user.service;

import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.enums.Role;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.mapper.UserMapper;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import com.likelion.springpractice.week11.domain.Badge;
import com.likelion.springpractice.week11.repository.BadgeRepository;
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
    private final BadgeRepository badgeRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        log.info("[서비스] 회원가입 시도: username = {}", request.getUsername());
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("[서비스] 이미 존재하는 사용자: username = {}", request.getUsername());
            throw new CustomException(UserErrorCode.USERNAME_ALREADY_EXISTS);
        }

        Badge defaultBadge = badgeRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("기본 뱃지를 찾을 수 없습니다."));

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        log.info("비밀번호 인코딩 완료: {}", encodedPassword);

        // 유저 엔티티 생성
        User user = User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .badge(defaultBadge)
                .build();

        // 저장 및 로깅
        log.info("User 객체 생성 완료. 저장 시도: {}", user);
        User savedUser = userRepository.save(user);
        log.info("[서비스] 회원가입 성공: username = {}", savedUser.getUsername());
        return userMapper.toSignUpResponse(savedUser);
    }
}