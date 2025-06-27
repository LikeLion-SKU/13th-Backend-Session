package com.likelion.springpractice.domain.review.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
  REVIEW_NOT_FOUND("REVIEW_404_01","리뷰를 찾을 수 없습니다.",HttpStatus.NOT_FOUND),
  UNAUTHORIZED_REVIEW_EDIT("REVIEW_403_01","리뷰 수정/삭제 권한이 없습니다.",HttpStatus.FORBIDDEN);

  private final String code;
  private final String message;
  private final HttpStatus status;
}

