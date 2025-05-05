package com.likelion.springpractice.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsConfig corsConfig;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        // CORS 설정
        .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))

        // CSRF 보호 기능 비활성화 (REST API에서는 필요없으므로 disable)
        .csrf(CsrfConfigurer::disable)

        // HTTP 요청에 대한 권한 설정 (인가 처리)
        .authorizeHttpRequests(auth ->
            auth.requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**")
                .permitAll()

                // 그 외 모든 요청 허용
                .anyRequest()
                .permitAll()
        )

        // 세션 관리 설정 (무상태 stateless로 관리)
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        // 브라우저 기본 로그인창 활성화
        .httpBasic(Customizer.withDefaults());

    return httpSecurity.build();
  }

  // 패스워드를 받고 자동으로 인코딩(암호화)할 수 있는 메소드
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
