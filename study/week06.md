## Spring Security란?

스프링 기반의 애플리케이션 보안을 담당하는 Spring 하위 framework

### 인증(Authentication)(auth)

- **신원을 검증**하는 행위

### 인가(Authorization)

- 사용자에게 특정 리소스나 기능에 **액세스할 수 있는 권한을 부여**하는 프로세스

## 로그인 인증 처리 절차 요약

사용자가 로그인 요청

→ HTTPServletRequest 이용해서 인증 토큰 생성

→ DB에 있는 사용자 정보를 가져와서 인증 처리

→ 일치할 경우 Success, 불일치할 경우 Failure

## Spring Boot 에서의 로그인 구현

1. Security Config 설정

   securityFilterChain, passwordEncoder 메소드 생성

2. UserDetailsService 상속한 클래스 생성

   로그인 시 입력한 username으로 사용자 조회하여 UserDetails 객체로 반환

3. UserDetails 상속한 클래스 생성

   필드 : username, password

## Access Token과 Refresh Token

JWT에서 사용하는 인증 수단

### Access Token

사용자의 로그인 인증 정보를 담고 있는 짧은 수명의 토큰

### Refresh Token

Access Token 이 만료됐을 때, 새로운 Access Token을 발급받기 위한 긴 수명의 토큰

## Security 설정을 위해 알아야 할 메소드

- requestMachers(): 특정 요청과 일치하는 url에 대한 액세스를 설정
- permitAll(): 누구나 접근이 가능하게 설정(인증/인가 없이)
- anyRequest(): permitAll 이외의 url에 대한 요청 설정
- authenticated(): 별도의 인가는 필요하지 않지만 인증이 성공된 상태여야 접근 가능
- BCryptPasswordEncoder(): 패스워드 인코더를 bean으로 등록

## CSRF(Cross-Site Request Forgery, 크로스 사이트 요청 위조)

공격자가 사용자의 권한을 빌려서, 사용자가 의도하지 않은 요청을 서버로 보내게 하는 공격 기법

## CORS(Cross-Origin Resource Sharing, 교차 출처 리소스 공유)

서로 다른 도메인 또는 서버 간에 자원을 요청하고 받을 때 발생하는 보안 제약을 해결하기 위한 방법

→ CORS 정책을 통해 해결

### Spring에서의 CORS 동작 방식

- 브라우저의 기본 동작: 웹 애플리케이션에서 다른 도메인으로 API 호출을 할 경우, 브라우저는
  이를 교차 출처 요청 으로 간주하고, 해당 요청을 서버에 보냄
- 서버의 응답: 서버는 Access-Control-Allow-Origin 헤더와 같은 CORS 관련 헤더를 포함하여 클라이언트에게 응답하고, 이 헤더는 서버가 요청을 허용할
  출처(Origin)를 명시함

### CORS의 주요 Header

Origin / Methods / Headers / Credentials / Expose-Headers / Max-Age

- Access-Control-Allow-Origin

  어떤 출처(origin)에서 요청을 허용할지 지정

- Access-Control-Allow-Methods

  어떤 HTTP 메서드(GET, POST, PUT, DELETE 등)를 허용할지 지정

- Access-Control-Allow-Methods Access-Control-Allow-Headers

  클라이언트가 요청 시 사용할 수 있는 헤더들을 정의

- Access-Control-Allow-Credentials

  true로 설정하면, 자격 증명(쿠키, HTTP 인증 등)을 포함한 요청을 허용

- Access-Control-Expose-Headers

  클라이언트가 응답에서 접근할 수 있는 헤더를 지정

- Access-Control-Max-Age

  브라우저가 CORS 응답을 얼마나 오랫동안 캐시할지를 지정

## 클라우드 컴퓨팅

인터넷(클라우드)을 통해 서버, 스토리지, 데이터베이스 등의 컴퓨팅 서비스를 제공