package com.likelion.springpractice.domain.foodlike.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FoodLikeErrorCode implements BaseErrorCode {
    FOODLIKE_ALREADY_EXISTS("FOODLIKE_4001", "이미 좋아요를 누른 음식입니다.", HttpStatus.BAD_REQUEST),
    FOODLIKE_NOT_FOUND("FOODLIKE_4002", "좋아요를 눌러야 취소할 수 있습니다.", HttpStatus.NOT_FOUND);
    
    private final String code;
    private final String message;
    private final HttpStatus status;
}
