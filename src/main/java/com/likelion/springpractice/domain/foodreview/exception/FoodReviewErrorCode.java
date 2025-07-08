package com.likelion.springpractice.domain.foodreview.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FoodReviewErrorCode implements BaseErrorCode {
    FOODREVIEW_ALREADY_EXISTS("FOODREVIEW_4001", "이미 리뷰를 작성한 음식입니다.", HttpStatus.BAD_REQUEST),
    FOODREVIEW_NOT_FOUND("FOODREVIEW_4002", "작성한 리뷰가 없는 음식입니다.", HttpStatus.NOT_FOUND);
    
    private final String code;
    private final String message;
    private final HttpStatus status;
}
