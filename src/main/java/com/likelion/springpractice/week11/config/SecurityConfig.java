//package com.likelion.springpractice.week11.config;
//
//import com.likelion.springpractice.week11.jwt.JwtSecurityConfig;
//import com.likelion.springpractice.week11.jwt.JwtTokenProvider;
//import com.likelion.springpractice.week11.service.CustomUserDetailsService;import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtTokenProvider jwtTokenProvider;
//    private final CustomUserDetailsService customUserDetailsService;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/users/login", "/api/users/register").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .apply(new JwtSecurityConfig(jwtTokenProvider, customUserDetailsService));
//        return http.build();
//    }
//}