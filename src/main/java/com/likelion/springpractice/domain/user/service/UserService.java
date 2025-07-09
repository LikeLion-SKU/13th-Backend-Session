package com.likelion.springpractice.domain.user.service;

import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateUserPasswordRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateUserRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdateUserResponse;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(UserErrorCode.USERNAME_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
            .email(request.getEmail())
            .username(request.getUsername())
            .password(encodedPassword)
            .country(request.getCountry())
            .introduce(request.getIntroduce())
            .build();

        User savedUser = userRepository.save(user);
        log.info("New user registered: {}", savedUser.getUsername());

        return userMapper.toSignUpResponse(savedUser);
    }

    @Transactional
    public UpdateUserResponse updateUserInformation(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        user.updateUserInformation(request.getUsername(), request.getIntroduce(),
            request.getCountry());

        return toUpdateUserResponse(user);
    }

    @Transactional
    public Boolean updateUserPassword(Long userId, UpdateUserPasswordRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        user.updateUserPassword(encodedPassword);
        
        return true;
    }

    private UpdateUserResponse toUpdateUserResponse(User user) {
        return UpdateUserResponse.builder().userId(user.getId())
                .username(user.getUsername())
            .country(user.getCountry()).introduce(user.getIntroduce()).build();
    }
}
