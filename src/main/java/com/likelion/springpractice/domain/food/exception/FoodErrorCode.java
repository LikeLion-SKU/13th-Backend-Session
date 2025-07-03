package com.likelion.springpractice.domain.food.exception;
import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum FoodErrorCode implements BaseErrorCode  {

  FOOD_NOT_FOUND("FOOD_4001", "존재하지 않는 음식입니다.", HttpStatus.NOT_FOUND),
  FOOD_ALREADY_EXISTS("FOOD_4002", "이미 존재하는 음식입니다.", HttpStatus.BAD_REQUEST);


  private final String code;
  private final String message;
  private final HttpStatus status;

}
