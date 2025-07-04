package com.likelion.springpractice.domain.food.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum FoodErrorCode implements BaseErrorCode {
  FOOD_NOT_FOUND("FOOD_4041", "존재하지 않는 음식입니다.", HttpStatus.NOT_FOUND);


  private final String code;
  private final String message;
  private final HttpStatus status;
}
