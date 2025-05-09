package com.likelion.springpractice.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsConfig corsConfig;

  @Value("${swagger.auth.username}")
  private String swaggerUsername;

  @Value("${swagger.auth.password}")
  private String swaggerPassword;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        // CORS 설정
        .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
        // CSRF 보호 기능 비활성화(REST API에서는 필요없음)
        .csrf(CsrfConfigurer::disable)
        // 세션 관리 - JWT 기반이므로 무상태
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .httpBasic(Customizer.withDefaults())

        // HTTP 요청에 대한 권한 설정
        // 권한 설정
        .authorizeHttpRequests(auth -> auth
            // 정적 리소스 허용
            .requestMatchers(
                "/favicon.ico", "/error"
            ).permitAll()

            // Swagger 리소스 개발자만 허용
            .requestMatchers(
                "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**"
            ).hasRole("DEVELOPER")

            // 인증 관련 요청 허용
            .requestMatchers("/api/auth/**", "/oauth2/**").permitAll()

            // 모든 api에 대한 접근 권한 허용
            .requestMatchers("/api/**").permitAll()

            // 관리자 전용 API
            .requestMatchers("/api/admin/**").hasRole("ADMIN")

            // 일반 사용자 API
            .requestMatchers("/api/user/**").authenticated()

            // 그 외 요청 차단
            .anyRequest().denyAll()
        );

    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails swaggerUser =
        User.builder()
            .username(swaggerUsername)
            .password(passwordEncoder().encode(swaggerPassword))
            .roles("DEVELOPER")
            .build();

    return new InMemoryUserDetailsManager(swaggerUser);
  }


}
