package com.likelion.springpractice.domain.own.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum OwnErrorCode implements BaseErrorCode {
  OWN_NOT_FOUND("OWN_4001", "존재하지 않는 배지 획득 현황입니다.", HttpStatus.NOT_FOUND),
  OWN_ALREADY_EXISTS("OWN_4002", "이미 존재하는 배지 획득 현황입니다.", HttpStatus.BAD_REQUEST);


  private final String code;
  private final String message;
  private final HttpStatus status;
}
