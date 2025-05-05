package com.likelion.springpractice.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        //환경변수에 정의된 출처만 허용
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        //리스트에 저장된 HTTP 메소드만 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        //리스트에 작성된 헤더들이 포함된 요청만 허용
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "Accept"));
        //쿠키나 인증 정보를 포함하는 요청 허용
        configuration.setAllowCredentials(true);
        //클라이언트가 Authorization 헤더를 읽을 수 있도록 허용(JWT사용할 경우)
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
