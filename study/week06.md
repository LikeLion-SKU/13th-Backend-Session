# 6주차 복습

다중 선택: 매주 과제

### Spring Security

스프링 기반의 애플라케이션 보안을 담당하는 Spring 하위의 프레임워크

- 필요한 데이터에 안전하고 빠르게 접근하도록 지원
- 고객 정보와 거래 데이터를 승인되지 않는 접근으로 부터 보호
- 고객 데이터를 효율적으로 관리

⇒ 웹개발 과정에서 전반적인 사용자 관리 기능을 구현하는데 기여

<aside>
📎

### Spring Security 아키텍쳐

사용자가 로그인을 시도할때, Spring Security가 내부적으로 어떤 과정을 거쳐서 인증을 수행하는지 단계별로 나타낸 구조도

### 1. HTTP Servlet Request : 사용자의 로그인 요청

사용자가 브라우저로 로그인 요청 전송, 요청에 ID와 Password 담겨있음, Authentication Filter(인증 필터)로 요청이 전달

### 2. UsernamePasswordAuthenticationToken 생성

Authentication Filter가 요청에서 ID와 Password를 꺼내어 UsernamePasswordAuthenticationToken 객체를 생성

→ 단순히 사용자가 이 ID와 Password로 로그인하려 한다는 정보만 담는 객체

ex) `new UsernamePasswordAuthenticationToken("userId", "password");`

### 3. AuthenticationManager에게 인증 요청

만들어진 UsernamePasswordAuthenticationToken 객체를 인증 처리하는 AuthenticationManager에게 전달

### 4. AuthenticationProvider에게 위임

AuthenticationManager는 직접 인증하지 않고 자신의 내부에 있는 실제로 인증을 수행하는 요소인 AuthenticationProvider들 중 하나에게 위임하여
사용자 인증을 수행하도록 함

### 5. UserDetailsService 호출

AuthenticationProvider는 특정 ID에 해당하는 유저 정보를 데이터베이스나 메모리에서 꺼내는 역할을 하는 UserDetailsService를 호출함

### 6. UserDetails 반환

UserDetailsService는 특정 ID에 해당하는 사용자 정보를 담고 있는 객체인 UserDetails를 반환

### 7. Password 검증

UserDetails에 있는 Password 와 요청으로 들어온 Password를 비교한다

이때, Password는 암호화되어있기 떄문에 PasswordEncoder를 이용하여 비교함

→ Password 가 일치하면 인증성공

### 8. 인증 완료 객체 반환

인증 성공 시, AuthenticationProvider는 인증이 완료된 Authentication 객체를 만들어서 AuthenticationManager에게 전달 → 인증 완료
및 권한을 갖고 있다는 정보가 담긴 객체

### 9. Authentication 객체 반환

AuthenticationManager는 인증된 Authentication 객체를 AuthenticationFilter에게 반환

### 10. SecurityContextHolder 에 저장

현재 로그인한 사용자 정보를 저장하는 SecurityContextHolder에 인증된 사용자 정보가 들어있는 Authentication을 저장함

인증 성공하면 AuthenticationSuccessHandler를 실행, 실패 시 AuthenticationFailureHandler 를 실행

</aside>

<aside>
📎

### 인증 vs. 인가

<aside>
📎

### 인증

사용자 신원을 검증하는 행위로 성공적으로 인증되어야만 액세스 가능

- ID 토큰을 이용해서 데이터를 전송
- 자격이 있는 사용자인지 확인하는 행위

</aside>

<aside>
📎

### 인가

신원이 검증된 사용자의 권한을 확인 후 특정 기능에 액세스 할 수 있는 권한을 부여하는 행위 ex) 일반 사용자는 관리자 페이지에 접근 불가

- 액세스 토큰을 이용하여 데이터를 전송
- 이미 인증된 사용자에게 특정 권한을 부여해도 되는지 확인하고 부여하는 행위

</aside>

</aside>

<aside>
📎

### Access Token

사용자가 인증받았음을 보여주는 토큰으로 API를 호출할때 사용

- 유효기간이 짧다
- 보안수준이 낮다

### Refresh Token

Access Token 이 만료되면 새로운 Access Token을 발급 받기 위한 토큰

- 유효기간이 길다
- 보안수준이 높다

**Access Token은 API를 호출할때 마다 전송해야하기에 노출 위험이 있음**

</aside>

<aside>
📎

### Security 설정을 위한 메소드

- requestMachers() : 특정 요청과 일치하는 URI에 대한 액세스를 설정
- permitAll( ) : 누구나 접근가능하도록 설정 즉, 인증 X 인가 X
- anyRequest() : permitAll 이외의 URI에 대한 요청 설정
- authenticated() : 별도 인가 필요X, BUT 인증은 성공된 상태여야 접근 가능하도록 설정
- BCryptPasswordEncoder() : Password Encoder를 bean 객체로 등록

### Security Config

```java
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
        .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
        .csrf(CsrfConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic(Customizer.withDefaults())
        // 인가처리 설정 부분 + HTTP 요청에 대한 권한 설정
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/favicon.ico", "/error"
                    ).permitAll()
                    .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**").hasRole("DEVELOPER")
                    .requestMatchers("/api/auth/**", "/oauth2/**").permitAll()
                    .requestMatchers("/api/**").permitAll()
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .requestMatchers("/api/user/**").authenticated()
                    // 그 외 모든 요청 허용
                    .anyRequest()
                    .denyAll()
        );
    return httpSecurity.build();
  }

  // 패스워드를 받고 자동으로 인코딩을 할 수 있게 하는 메소드 -> 비밀번호 암호화가 가능해진다
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

```

</aside>

<aside>
📎

### CSRF

Cross-Site Request Forgery : 크로스 사이트 요청 위조

사용자의 권한을 빌려서 사용자가 의도하지 않은 요청을 서버로 보내도록 하는 공격 기법

→ 사용자의 권한만 빌리게 되면 서버가 해당 요청이 유효하다고 판단하여서 공격 당할 수 있음

**Spring Security 에서는 CSRF 보호가 기본으로 활성화 + RESTful API를 이용한 서버는 stateless이기에 서버에 인증정보를 보관하지 않음 따라서
CSRF 공격 방지할 필요 X**

</aside>

<aside>
📎

### CORS

Cross-Origin Resource Sharing : 교차 출처 리소스 공유

서로 다른 도메인이나 서버 간의 자원을 요청하고 전송받을때 발생하는 보안 제약을 해결하는 방법

ex)
SpringBoot : http://localhost:8080
React : http://localhost:3000

이런식으로 서로 다른 도메인 간의 HTTP 요청은 Cross-Origin(교차 출처) 로 간주되어 보안상의 이유로 제한하지만 CORS 정책으로 이를 해결 가능

### CORS 동작 방식

1. 다른 도메인으로 HTTP 요청을 할 경우 브라우저가 교차-출처로 인식하여 요청을 서버로 전송
2. 서버는 CORS 관련 헤더(HTTP 요청을 허용할 출처)를 포함해서 클라이언트에 전송

### CORS 주요 Header

Access-Control-Allow-Origin : 어떤 출처에서 요청을 허용할지 지정

Access-Control-Allow-Methods : 어떤 HTTP 메소드를 허용할지 지정

Access-Control-Allow-Header : 클라이언트 요청 시 사용가능항 헤더 정의

Access-Control-Allow-Credentials : true 설정시, 자격증명을 포함한 요청을 허용

Access-Control-Expose-Headers : 서버로부터 온 응답에서 클라이언트가 접근 가능 헤더 지정

Access-Control-Max-Age : 브라우저가 CORS 응답을 얼마나 오랫동안 캐시할지 지정

### CorsConfig

```java
package com.likelion.springpractice.global.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

  // ip와 port를 유출되지 않도록 이런식으로 값을 가져옴
  @Value("${cors.allowed-origins}")
  private String[] allowedOrigins;

  @Bean
  public UrlBasedCorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    // 환경 변수에 정의된 출처만 허용
    configuration.setAllowedOrigins(Arrays.asList(allowedOrigins)); // 유출되지 않도록 환경변수로 관리
    //리스트에 작성한 HTTP 메소드 요청만 허용
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
    // 리스트에 적성한 헤더들이 포함된 요청만 허용
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "ContentType", "Accept"));
    //쿠키나 인증정보를 포함하는 요청 허용
    configuration.setAllowCredentials(true);
    // 수정 , 클라이언트가 Authorization 헤더를 읽을 수 있도록 허용(JWT 를 사용한 경우)
    configuration.setExposedHeaders(List.of("Authorization"));
    // 모든 경로에 대해 위의 CORS 설정을 적용
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}

```

</aside>

<aside>
📎

### EC2 : Elastic Compute Cloud

AWS에서 제공하는 클라우드 컴퓨팅 서비스

- 서버, 스토리지, 데이터베이스 등 컴퓨터 서비스를 제공

</aside>