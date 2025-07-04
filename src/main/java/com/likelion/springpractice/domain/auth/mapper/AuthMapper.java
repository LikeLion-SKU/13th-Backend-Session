//package com.likelion.springpractice.domain.auth.mapper;
//
//import com.likelion.springpractice.domain.auth.dto.response.LoginResponse;
//import com.likelion.springpractice.week11.domain.User;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthMapper {
//
////    public LoginResponse toLoginResponse(User user, String accessToken, Long expirationTime) {
//public LoginResponse toLoginResponse(com.likelion.springpractice.week11.domain.User user, String accessToken, Long expirationTime) {
//    return LoginResponse.builder()
//                .accessToken(accessToken)
//                .userId(user.getId())
//                .username(user.getNickname())
////                .role(user.getRole())
//                .expirationTime(expirationTime)
//                .build();
//    }
//}