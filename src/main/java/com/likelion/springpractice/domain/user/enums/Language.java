package com.likelion.springpractice.domain.user.enums;

import lombok.Getter;

@Getter
public enum Language {
  KO("한국어"),
  EN("영어"),
  JA("일본어");

  private final String displayName;

  Language(String displayName) {
    this.displayName = displayName;
  }

}
