package com.likelion.springpractice.domain.badge.enums;

public enum BadgeType {
  // 후기 5개
  BABY_BADGE("아기배찌"),
  // 후기 15개
  SPOON_BADGE("숟가락배찌"),
  // 후기 30개
  SPOON_SET_BADGE("수저세트배찌"),
  // 후기 50개
  ICON_BADGE("우리 서버 아이콘배찌");

  private final String displayName;

  BadgeType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}