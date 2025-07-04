package com.likelion.springpractice.domain.food.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FoodErrorCode implements BaseErrorCode {

  FOOD_NOT_FOUND("FOOD_4001", "존재하지 않는 음식입니다.", HttpStatus.NOT_FOUND),
  INVALID_FOOD_ID("FOOD_4002", "잘못된 음식 ID입니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus status;
}