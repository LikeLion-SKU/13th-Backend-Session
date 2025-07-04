package com.likelion.springpractice.domain.auth.service;

//import com.likelion.springpractice.domain.auth.dto.request.LoginRequest;
import com.likelion.springpractice.week11.dto.request.UserLoginRequestDto;
//import com.likelion.springpractice.domain.auth.dto.response.LoginResponse;
//import com.likelion.springpractice.domain.auth.mapper.AuthMapper;
//import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.week11.domain.User;
import com.likelion.springpractice.week11.dto.response.UserLoginResponseDto;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.global.jwt.JwtProvider;
import com.likelion.springpractice.week11.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
//    private final AuthMapper authMapper;

//    public LoginResponse login(LoginRequest loginRequest) {
//public UserLoginResponseDto login(LoginRequest loginRequest) {
public UserLoginResponseDto login(UserLoginRequestDto loginRequest) {
        // 사용자 조회
//        User user = userRepository.findByEmail(loginRequest.getUsername())
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 인증 처리
        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
     authenticationManager.authenticate(authenticationToken);

        // 액세스 토큰 및 리프레시 토큰 발급
        String accessToken = jwtProvider.createAccessToken(user.getEmail());
        String refreshToken = jwtProvider.createRefreshToken(user.getEmail(), UUID.randomUUID().toString());
        log.info("로그인 성공: {}", user.getEmail());

        // 리프레시 토큰 저장
        user.createRefreshToken(refreshToken);

        // Access Token의 만료 시간 가져옴
//        Long expirationTime = jwtProvider.getExpiration(accessToken);

        // 로그인 성공 로그
        log.info("로그인 성공: {}", user.getEmail());

        // 로그인 응답 반환
        return new UserLoginResponseDto(
                user.getId(),
                user.getNickname(),
                accessToken
        );
    }}