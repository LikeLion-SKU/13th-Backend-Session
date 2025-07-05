package com.likelion.springpractice.domain.mission.entity;

public enum Country {
  KOREAN("대한민국"),
  AMERICAN("미국"),
  JAPANESE("일본"),
  OTHER("기타");

  private final String koreanName;

  Country(String koreanName) {
    this.koreanName = koreanName;
  }

  public String getKoreanName() {
    return koreanName;
  }
}


