package com.likelion.springpractice.week11.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponseDto {
    private Long userId;
    private String nickname;
    private String token;
}