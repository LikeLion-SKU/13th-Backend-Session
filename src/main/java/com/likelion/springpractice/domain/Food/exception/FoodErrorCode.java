package com.likelion.springpractice.domain.Food.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FoodErrorCode implements BaseErrorCode {

  FOOD_NOT_FOUND("FOOD_4001", "해당 음식을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  INVALID_SEARCH_KEYWORD("FOOD_4002", "유효한 검색어가 아닙니다.", HttpStatus.BAD_REQUEST),
  NO_SEARCH_RESULT("FOOD_4003", "검색 결과가 없습니다.", HttpStatus.NOT_FOUND);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
