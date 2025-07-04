package com.likelion.springpractice.week11.service;

import com.likelion.springpractice.week11.domain.User;
import com.likelion.springpractice.week11.domain.Badge;
import com.likelion.springpractice.week11.dto.request.UserRegisterRequestDto;
import com.likelion.springpractice.week11.dto.request.UserLoginRequestDto;
import com.likelion.springpractice.week11.dto.response.UserLoginResponseDto;
import com.likelion.springpractice.week11.dto.response.UserResponseDto;
import com.likelion.springpractice.week11.jwt.JwtTokenProvider;
import com.likelion.springpractice.week11.repository.UserRepository;
import com.likelion.springpractice.week11.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public void registerUser(UserRegisterRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Badge defaultBadge = badgeRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("기본 뱃지를 찾을 수 없습니다."));

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .nationality(request.getNationality())
                .introduction(request.getIntroduction())
                .badge(defaultBadge)
                .build();

        userRepository.save(user);
    }

    // 로그인
    public UserLoginResponseDto login(UserLoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(user.getEmail(), user.getId());

        return new UserLoginResponseDto(user.getId(), user.getNickname(), token);
    }

    // 마이페이지 - 사용자 프로필
    public UserResponseDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getNationality(),
                user.getBadge().getName()
        );
    }
}