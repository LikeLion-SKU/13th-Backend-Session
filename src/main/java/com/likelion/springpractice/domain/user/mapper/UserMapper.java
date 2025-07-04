package com.likelion.springpractice.domain.user.mapper;

import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public SignUpResponse toSignUpResponse(User user) {
        return SignUpResponse.builder()
            .userId(user.getId())
            .email(user.getEmail())
            .username(user.getUsername())
            .password(user.getPassword())
            .country(user.getCountry())
            .introduce(user.getIntroduce())
            .build();
    }
}
