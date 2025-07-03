package com.likelion.springpractice.domain.favoritefood.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FavoriteFoodErrorCode implements BaseErrorCode {

  FOOD_NOT_FOUND("FOOD_4041", "존재하지 않는 음식입니다.", HttpStatus.NOT_FOUND),
  ALREADY_LIKED("FOOD_4091", "이미 좋아요한 음식입니다.", HttpStatus.CONFLICT),
  FAVORITE_NOT_FOUND("FOOD_4042", "좋아요한 음식이 아닙니다.", HttpStatus.NOT_FOUND);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
