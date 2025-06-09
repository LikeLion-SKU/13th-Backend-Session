package com.likelion.springpractice.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Country {
    @Schema(description = "대한민국")
    KOREA,
    @Schema(description = "미국")
    USA,
    @Schema(description = "중국")
    CHINA,
    @Schema(description = "일본")
    JAPAN;
}
