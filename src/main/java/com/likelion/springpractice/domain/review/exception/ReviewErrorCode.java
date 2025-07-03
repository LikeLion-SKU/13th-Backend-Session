package com.likelion.springpractice.domain.review.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

  REVIEW_NOT_FOUND("REVIEW_4041", "존재하지 않는 리뷰입니다.", HttpStatus.NOT_FOUND),
  UNAUTHORIZED_REVIEW_ACCESS("REVIEW_4031", "리뷰에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
