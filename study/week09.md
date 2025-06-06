# Spring Boot (Week 9) - JWT(JSON Web Token) 개념 및 설계 정리

## JWT
- 로그인 이후 인증 정보를 안전하게 주고받는 토큰 기반 인증 방식입니다.
- 구조는 `[Header].[Payload].[Signature]` 세 부분으로 구성되며, `.`으로 구분됩니다.
- 서버는 Stateless하게 요청을 검증하고, 클라이언트는 토큰을 저장하고 사용합니다.

---

## 구성요소

### 1. Header
- 토큰의 타입(`typ`)과 서명 알고리즘(`alg`) 명시
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

---

### 2. Payload
- 실제 사용자 정보(Claim)를 담는 부분

```json
{
  "sub": "user@example.com",
  "exp": 1716239022,
  "role": "USER"
}
```

#### Claim 종류
| 유형 | 설명 | 예시 |
|------|------|------|
| 등록 (Reserved) | 표준 필드 | `iss`, `sub`, `exp`, `iat` |
| 비공개 (Private) | 사용자 정의 | `userId`, `role` 등 |
> ⚠️ 비밀번호 등 민감 정보는 절대 포함 X

---

### 3. Signature
- 변조 방지를 위한 서명
```plaintext
HMACSHA256(
  base64UrlEncode(Header) + "." + base64UrlEncode(Payload),
  secret
)
```

---

## JWT 동작 원리

1. 사용자 로그인 요청
2. 서버가 사용자 인증 후 JWT 발급 (Access + Refresh)
3. 클라이언트가 JWT 저장 (localStorage, memory 등)
4. 이후 요청 시 `Authorization: Bearer <Access Token>` 포함
5. 서버는 JWT 디코딩 → 사용자 검증 후 응답
6. Access Token 만료 시 Refresh Token으로 재발급 요청

---

## Access Token

- API 접근에 사용되는 신분증 역할의 토큰
- 유효기간: 보통 5~30분
- Stateless: 서버는 토큰 자체를 저장하지 않음
- 클라이언트는 메모리 또는 Secure 쿠키에 저장

---

## Refresh Token

- Access Token 만료 시 재발급 용도
- 유효기간: 7일~수개월
- 보안: HttpOnly + Secure 쿠키에 저장
- 서버에 저장하여 재사용 방지 및 무효화 가능 (DB 또는 Redis 활용)

---

## Access vs Refresh Token

| 항목 | Access Token | Refresh Token |
|------|---------------|----------------|
| 역할 | 인증 (API 접근) | 재인증 (Access 재발급) |
| 유효기간 | 짧음 (5~30분) | 김 (7일~수개월) |
| 저장 위치 | 클라이언트 메모리 / 쿠키 | HttpOnly 쿠키 |
| 서버 저장 | ❌ 저장하지 않음 | ✅ 저장 필요 |
| 사용 방식 | 요청 헤더에 포함 | `/refresh` 요청에 포함 |

---

## 보안 고려사항

### Access Token
- 민감 정보는 포함 금지
- HTTPS로만 전달
- 유효기간은 짧게 설정

### Refresh Token
- HttpOnly + Secure 쿠키 사용
- 서버에 저장하여 재사용 감지 및 무효화 처리

---

## 토큰 흐름 요약

1. 로그인 시 서버에서 Access + Refresh Token 발급
2. API 요청 시 Access Token을 헤더에 담아 전송
3. Access Token 만료 → `/refresh` 요청으로 새로 발급
4. 로그아웃 시 서버와 쿠키에서 Refresh Token 삭제

---

## JWT 구현 순서 (Spring Security 예시)

1. 환경 구성 (JDK 확인)
2. User & Repository 작성
3. JWT 유틸 작성 (발급/검증)
4. UserDetails 구현
5. Spring Security 설정 (필터 등록)
6. JwtAuthenticationFilter 구현
7. AuthController 작성
8. 테스트 및 예외 처리
