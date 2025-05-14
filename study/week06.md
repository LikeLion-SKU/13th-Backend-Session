# Spring Boot (Week 6)
## - Spring Boot에서 JWT 기반 로그인 구현 과정

## 1. 회원가입

- 사용자가 email, password 등을 입력하여 회원가입 요청을 보냄
- 서버는 이를 받아서 `User` 엔티티로 저장함
- 이때 비밀번호는 **`BCryptPasswordEncoder`** 등을 사용해 암호화 후 저장

```java
passwordEncoder.encode(rawPassword);
```

---

## 2. 로그인 요청 (`POST /login`)

- 사용자가 email과 password를 입력하고 로그인 요청을 보냄
- 서버는 해당 email로 사용자를 조회하고, 비밀번호를 `BCryptPasswordEncoder.matches()`로 비교함
- 인증에 성공하면 JWT 토큰을 발급함

---

## 3. Access Token, Refresh Token 발급

### Access Token
- 짧은 유효 기간 (보통 15분 ~ 1시간)
- **사용자의 인증 정보(id, email, roles 등)를 담고 있음**
- 모든 API 요청 시 `Authorization: Bearer <access_token>` 헤더에 포함해서 보냄

### Refresh Token
- 긴 유효 기간 (보통 7일 ~ 30일)
- Access Token이 만료됐을 때 새로운 Access Token을 재발급받기 위해 사용
- 보안을 위해 서버(DB/Redis)에 저장하거나, `HttpOnly Cookie`에 저장

| 구분            | Access Token                           | Refresh Token                          |
|-----------------|-----------------------------------------|----------------------------------------|
| 목적            | 인증된 사용자임을 증명                  | Access Token 재발급                    |
| 수명            | 짧음 (15분~1시간)                 | 김 (보통 7일~30일)                     |
| 저장 위치       | 메모리 / localStorage / cookie         | HttpOnly 쿠키 or DB 저장               |
| 서버 저장 필요  | ❌ Stateless                            | ⭕ 보통 Redis 또는 DB에 저장            |
| 유출 위험 시    | 짧은 시간 동안만 악용 가능              | 장기 위험 → 엄격한 보안 관리 필요      |

---

## 4. JWT 토큰 구조

JWT는 `Header.Payload.Signature`의 구조로 이루어져 있음.

- **Header**: typ(JWT), alg(HS256 등)
- **Payload**: 사용자 정보, 토큰 만료 시간, 권한
- **Signature**: 서버의 비밀키로 서명된 문자열

---

## 5. 클라이언트는 Access Token을 저장

- 로컬 스토리지, 세션 스토리지, 혹은 메모리에 저장 가능
- 이후 인증이 필요한 요청 시 헤더에 토큰을 포함시켜 전송:

```http
Authorization: Bearer <access_token>
```

---

## 6. 서버에서 인증 필터로 토큰 검사

- 스프링 시큐리티 필터 체인에서 **JWT 인증 필터**를 만들고 등록
- 요청이 들어올 때 `Authorization` 헤더에 토큰이 있는지 확인하고
- 유효하면 SecurityContext에 인증 정보를 저장하여 요청을 통과시킴

---

## 7. Access Token 만료 → Refresh Token으로 재발급 요청

- 클라이언트는 API 응답에서 "토큰 만료" 에러가 발생하면,
- `/reissue` 또는 `/token/refresh` 등의 API로 Refresh Token을 전송
- 서버는 해당 Refresh Token을 검증하고 → 새 Access Token을 생성하여 응답

---

## 8. 로그아웃 처리

- 클라이언트는 저장한 Access/Refresh Token을 삭제
- 서버는 Refresh Token을 저장하고 있다면 삭제하여 재사용 방지

---

## 9. 보안 고려사항

- Access Token은 비교적 수명이 짧으므로 클라이언트가 보관
- Refresh Token은 서버 저장 (DB/Redis) 또는 `HttpOnly Cookie`로 보관
- HTTPS를 통해서만 통신해야 함
- 토큰 탈취 및 재사용 방지를 위해 **토큰 재발급 시 기존 토큰 무효화 처리 필요**


---

## 로그인 흐름 요약

1. 클라이언트가 `/login`으로 ID/PW 전송
2. 서버에서 DB 유저 정보 조회 후 비밀번호 비교
3. 성공 시 Access Token, Refresh Token 발급
4. 클라이언트는 Access Token으로 인증된 요청 수행
5. Access Token 만료되면 Refresh Token으로 재발급 요청

---

# `6주차 세션 정리`

## 1. Spring Security 개요

- Spring 애플리케이션 보안 담당 프레임워크
- 인증(Authentication), 인가(Authorization) 처리
- CSRF, CORS, 세션, 필터, 암호화 등 포함

## 2. 인증 흐름 요약

1. 사용자가 로그인 요청
2. AuthenticationManager가 요청 처리
3. UserDetailsService가 DB에서 사용자 조회
4. 성공 시 SecurityContextHolder에 인증 정보 저장

## 3. 주요 클래스/컴포넌트

- `AuthenticationManager`: 인증 관리
- `UserDetailsService`: 사용자 정보 조회
- `UserDetails`: 사용자 정보 객체
- `UsernamePasswordAuthenticationToken`: 인증 객체
- `SecurityContextHolder`: 인증 정보 저장소

## 4. 인증 vs 인가

| 구분       | 인증 (Authentication)     | 인가 (Authorization)         |
|------------|----------------------------|-------------------------------|
| 의미       | 사용자 본인 확인            | 자원 접근 권한 확인           |
| 시점       | 로그인 시                  | 로그인 후 특정 자원 접근 시   |

## 5. 보안 설정 예시

```java
http
  .csrf().disable()
  .authorizeHttpRequests()
    .requestMatchers("/login", "/signup").permitAll()
    .anyRequest().authenticated();
```

## 6. CSRF & CORS 요약

- **CSRF**: 세션 기반 공격 방지용 → JWT 기반 로그인에서는 보통 비활성화
- **CORS**: 다른 도메인 간 요청 허용 → 헤더로 제어

```http
Access-Control-Allow-Origin: *
Access-Control-Allow-Methods: GET, POST
Access-Control-Allow-Headers: Authorization, Content-Type
```

## 7. Token 저장 위치

- Access Token → 클라이언트 저장
- Refresh Token → 서버 저장 (DB/Redis), 또는 HttpOnly 쿠키 사용

---

## 8. EC2 실습 요약

- `.pem` 키 사용해 Mac에서 SSH 접속
- `chmod 400` 권한 설정 필수
- `ssh -i ~/.ssh/key.pem ubuntu@<IP>` 또는 `~/.ssh/config` 설정으로 간편 접속
- 보안 그룹에 포트 22 열어야 SSH 접속 가능
