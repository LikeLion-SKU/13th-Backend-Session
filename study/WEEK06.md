## ✅ Access Token (엑세스 토큰)

- **역할**: 사용자의 인증 상태를 증명하는 *짧은 수명의 토큰*
- **사용 위치**: API 요청 시 `Authorization` 헤더에 담아 전송
- **수명**: 짧음 (예: 15분~1시간)
- **특징**:
    - 서버는 이 토큰을 검증하여 요청을 허용/거부
    - 만료되면 사용 불가 → 새 토큰 필요
- **보안 이슈**: 탈취되면 일정 시간 동안 악용 가능

---

## 🔁 Refresh Token (리프레시 토큰)

- **역할**: Access Token이 만료됐을 때 *새로운 Access Token을 발급받기 위한 토큰*
- **사용 위치**: 주로 HTTP-only Cookie 또는 보안 저장소
- **수명**: 길음 (예: 2주~1개월)
- **특징**:
    - 서버에서만 검증됨 (DB 또는 Redis 등에서 관리)
    - 탈취 시 장기적인 위험 → 매우 민감하게 다뤄야 함
- **보안 전략**:
    - 재사용 방지 (rotation)
    - IP, User-Agent 검증
    - 탈취 시 강제 로그아웃 처리

---

## 🔄 흐름 요약

1. 사용자 로그인 요청
2. HTTPServletRequest에 ID/PW 담김
3. AuthenticationFilter가 정보 유효성 검사
4. UsernamePasswordAuthenticationToken 생성
5. AuthenticationManager가 AuthenticationProvider에게 인증 위임
6. Provider는 UserDetailsService를 통해 사용자 정보 조회
7. 조회된 정보로 UserDetails 객체 생성
8. 입력 정보와 비교 → 인증 완료
9. 성공: SecurityContextHolder에 저장 후 SuccessHandler 호출
10. 실패: FailureHandler 호출

---