package com.likelion.springpractice.domain.review.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
  REVIEW_ERROR_CODE("REVIEW_4041", "작성한 리뷰가 없습니다.", HttpStatus.NOT_FOUND),
  DUPLICATE_REVIEW_ERROR_CODE("REVIEW_4001", "이미 해당 음식에 리뷰를 작성하였습니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
