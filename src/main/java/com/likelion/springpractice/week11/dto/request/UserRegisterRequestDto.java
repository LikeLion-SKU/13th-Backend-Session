package com.likelion.springpractice.week11.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String nationality;
    private String introduction;
}