✅ JWT 핵심 개념 요약

## JWT란?

JSON Web Token
사용자 인증 정보를 안전하게 주고받기 위한 토큰 기반 인증 방식
3개의 파트로 구성: Header.Payload.Signature

---

## 구성 요소

Header: 암호화 방식 (예: HS256)
Payload:claim 이라고 부르는 전달하려는 정보(예: userId, role, exp)
Signature: 헤더와 정보를 합친후 서버가 지정한 secret key로 암호화 시켜 토큰을 변조하기 어렵게 만들어줌 (변조 방지)



---

## JWT의 동작원리

1. 사용자 로그인
2. 사용자 확인 <-> 회원 DB
3. Access Token(JWT)발급
4. 응답(+Access Token)
5. 데이터 요청(+JWT)
6. Access Token 검증
7. 응답 (+ 요청 데이터)

---

✅ Access Token vs Refresh Token
Access Token 인증된 사용자가 API에 접근할 수 있게 하는 신분증 (짧은 유효기간, 서버 저장 X)
Refresh Token Access Token이 만료되었을 때 새로운 토큰을 발급받기 위한 열쇠 (긴 유효기간, 서버 저장 O)

⚠️ Access Token은 Memory나 Secure Cookie,
⚠️ Refresh Token은 HttpOnly + Secure 쿠키 사용 권장

---

✅ JWT 동작 흐름
로그인 → accessToken + refreshToken 발급
클라이언트 → accessToken을 헤더에 담아 API 요청
accessToken 만료 시 → refreshToken으로 재발급 요청 (/refresh)
로그아웃 시 → refreshToken 삭제 & 쿠키 제거

---

✅ 보안 설계 시 고려사항
Access Token: 유효기간 짧게, JS 접근 불가능한 쿠키 사용
Refresh Token: DB나 Redis에 저장, 재사용 탐지 및 무효화 전략 필요
민감한 정보는 Payload에 넣지 않기
반드시 HTTPS 통신

---

✅ JWT 구현 순서 요약

1. 환경 구축(의존성&JDK21 확인)
2. User & Repository 작성
3. JWT 유틸리티 작성 (토큰 발급/검증)
4. UserDetails 구현(Spring Secuity용)
5. Spring Security 설정 (JWT 필터 등록)
6. JwtAuthenticationFilter 구현
7. AuthController 작성 (로그인/회원가입)
8. 테스트 & 예외 처리

---