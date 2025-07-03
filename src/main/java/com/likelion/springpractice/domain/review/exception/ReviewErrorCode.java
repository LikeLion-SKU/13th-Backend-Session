package com.likelion.springpractice.domain.review.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
  REVIEW_NOT_FOUND("REVIEW_4001", "존재하지 않는 후기입니다.", HttpStatus.NOT_FOUND),
  REVIEW_ALREADY_EXISTS("REVIEW_4002", "이미 존재하는 후기입니다.", HttpStatus.BAD_REQUEST),
  NO_PERMISSION_TO_UPDATE("REVIEW_4003", "후기 작성자와 회원 정보가 일치하지 않습니다.", HttpStatus.NOT_FOUND),
  INVALID_FOOD_ID_FOR_REVIEW_UPDATE("REVIEW_4004", "후기 음식 정보와 음식 정보가 일치하지 않습니다.", HttpStatus.NOT_FOUND);
  
  
  private final String code;
  private final String message;
  private final HttpStatus status;
}
