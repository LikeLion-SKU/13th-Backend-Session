<details>
  
  <summary>9주차 미션</summary>

  <details>
    <summary>9주차 정리</summary>

## JWT란?
 JSON Web Token, JSON 형식의 데이터를 담은 토큰
- JSON 형식으로 사용자 인증 정보를 안전하게 주고받기 위한 토큰 기반 인증 방식, .으로 구분된 3개의 파트로 구성됨
- 로그인 후 사용자 정보를 안전하게 클라이언트에 저장하고 서버는 Stateless(무상태)하게 인증을 처리할 수 있음
![image](https://github.com/user-attachments/assets/916b87c3-d407-4cc3-b935-9548ff965861)

## JWT의 구성요소
**Header.Payload.Signature**
- Header : 어떠한 알고리즘으로 암호화 할 것인지, 어떠한 토큰을 사용할 것 인지에 대한 정보
- Example :  { "alg": "HS256", "typ": "JWT" }
  
- Payload :  claim이라고 부르는 전달하려는 정보 (사용자 id나 다른 데이터들)
- Example :  { "sub": "user@example.com", "exp": 1716239022, "role": "USER" }
  
- Signature : 헤더와 정보를 합친 후 서버가 지정한 secret key로 암호화 시켜 토큰을 변조하기 어렵게 만들어줌
- Example : 토큰이 발급된 후 누군가가 Payload의 정보를 수정하면 Payload에는 다른 누군가가 조작된 정보가 들어가 있지만 Signatute에는 수정되기 전의 Payload 내용을 기반으로 이미 암호화 되어있는 결과가 저장되어 있기 때문에 조작되어있는 Payload와는 다른 결과값이 나오게 됨

### JWT의 동작원리
![image](https://github.com/user-attachments/assets/eb16603a-5402-497c-84eb-1ac5028eb5f2)

## AccessToken이란?
역할: **사용자가 인증(로그인) 후, 보호된 리소스(API 등)에 접근할 때 필요한 “신분증"**

- 클라이언트(브라우저/앱)가 매 요청 시 API 호출에 이 토큰을 헤더에 담아 보냄
- 보통 짧은 유효기간 (5~30분 정도가 일반적)
- 서버는 토큰을 디코딩해서 사용자 정보(예: userId, role 등)를 꺼냄

### AccessToken 사용 Tip
- 서버는 Access Token을 별도로 저장하지 않음 (Stateless)
- Token에 Role을 담으면 권한 처리에도 활용 가능 (ROLE_USER, ROLE_ADMIN)
- 클라이언트에서 로컬 스토리지보다는 Memory / Secure Cookie 사용 권장
- 로그아웃 시 Refresh Token 삭제, Access Token은 블랙리스트 저장

## Refresh Token이란?
역할 : **액세스 토큰이 만료되었을 때, 새로운 액세스 토큰을 발급받기 위한 “열쇠”**

- 액세스 토큰처럼 Authorization 헤더에 쓰지 않고, 보통 HttpOnly 쿠키로 저장해서 클라이언트가 직접 다루지 않도록 함 
- 보통 긴 유효기간 (7일 ~ 몇 달까지도 설정)
- 서버가 DB 등에 저장해서 유효성을 검증할 수 있도록 관리

### Refresh Token 사용 Tip
- 로그아웃 시 서버에 저장된 Refresh Token 삭제 필수
- 서버에서 토큰과 사용자 매핑 테이블 관리 권장 (userId, refreshToken, 만료시간 등)
- Redis로 저장할 경우: refresh:{userId} 키 형식 + TTL 설정
- 강제 로그아웃, 강제 탈퇴 등에도 Refresh Token 삭제 필요

### AccessToken vs Refresh Token
![image](https://github.com/user-attachments/assets/d86656e9-c7cc-4d39-b56a-67a53516ce99)

## Token 설계 시 보안 고려사항
### Access Token
- 토큰 기간은 짧게 유지 (만료시간 짧게)
- JWT에는 민감한 정보(패스워드 등) 절대 넣지 않음
- HTTPS로만 통신

### Refresh Token
- HttpOnly + Secure 쿠키로 관리 (JS 접근 차단)
- DB에 저장해서 유효성 확인 (블랙리스트 전략)
- 탈취 시를 대비해 재사용 감지(Reuse Detection) 및 무효화 전략 추가 가능

# 토큰 흐름 정리
### [1] 사용자 로그인 요청
- 서버: accessToken + refreshToken 발급

### [2] API 호출
- 프론트는 API 호출 시 매번 accessToken 
- 헤더에 담아 보냄

### [3] 액세스 토큰 만료
- 프론트가 API 호출 시 401 오류 받음 
 → 새 액세스 토큰 요청

### [4] 토큰 재발급
- 서버에 /refresh 요청 (쿠키의 리프레시 토큰 자동 전송) → 새 액세스 토큰 발급

--- 
### 로그인 응답
- 본문: accessToken + 사용자 정보
- 쿠키: refreshToken (HttpOnly + Secure)

### 액세스 토큰 재발급 흐름:
- /refresh 엔드포인트
- 쿠키에 있는 리프레시 토큰을 사용해서 새 액세스 토큰 발급

### 로그아웃
- DB에서 리프레시 토큰 삭제
- 쿠키 제거 (Set-Cookie: refreshToken=; Max-Age=0)
--- 

### JWT 구현 순서
1. 환경 구축 (의존성 & JDK 21 확인)
2.User & Repository 작성
3. JWT 유틸리티 작성 (토큰 발급/검증)
4. UserDetails 구현 (Spring Security 용)
5. Spring Security 설정 (JWT 필터 등록)
6. JwtAuthenticationFilter 구현
7. AuthController 작성 (로그인/회원가입)
8. 테스트 & 예외 처리

  </details>

  <details>
    <summary>JWT - Header,Payload,Signature</summary>


## JWT란?

JWT는 **사용자의 인증 정보를 담는 토큰 형식**
로그인 후 발급된 JWT는 사용자의 정보를 포함하고 있어서, 서버가 매번 DB를 조회하지 않고도 사용자를 식별할 수 있다.

JWT는 다음과 같은 **3가지 부분**으로 구성:

```
Header.Payload.Signature
```

각 부분은 **Base64로 인코딩**된 문자열이고, `.`으로 구분.

---

## 1. Header (헤더)

### 📌 역할:

JWT의 **타입**과 **서명 방식**을 지정하는 부분

### 📦 담기는 내용 예시:

```json
{
  "alg": "HS256",   // 서명 알고리즘: HMAC-SHA256
  "typ": "JWT"      // 토큰 타입: JWT
}
```

---

## 2. Payload (페이로드)

### 📌 역할:

**사용자 정보(Claims)** 를 담고 있는 부분
→ 로그인한 사용자가 누군지, 어떤 권한을 갖고 있는지를 담는다

### 📦 담기는 내용 예시:

```json
{
  "sub": "user123",            // subject: 유저 ID
  "name": "현준",              // 이름 같은 추가 정보
  "role": "USER",              // 권한 정보
  "iat": 1717058400,           // 발급 시간 (Issued At)
  "exp": 1717062000            // 만료 시간 (Expiration)
}
```

> **주의:** 이 부분은 암호화되지 않음 → 누구나 읽을 수 있으므로 **민감 정보는 넣으면 안 됨!**

---

## 3. Signature (서명)

### 📌 역할:

**토큰의 위변조를 방지**하기 위한 서명.
Header와 Payload가 중간에 **수정되지 않았다는 걸 검증**하기 위해 존재함.

### 🔐 생성 방식:

1. Header와 Payload를 각각 Base64 인코딩
2. 이 둘을 `.`으로 이어붙임
3. 이어붙인 문자열을 **서버의 비밀키(Secret Key)** 로 HMAC-SHA256 방식으로 서명
4. 그 결과가 Signature

### 🔧 예시:

```text
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secretKey
)
```

---

## 요약

| 구성 요소     | 설명              | 민감 정보 저장 가능? |
| --------- | --------------- | ------------ |
| Header    | 서명 방식, 타입       | ❌            |
| Payload   | 사용자 정보 (claims) | ❌ (암호화 안됨)   |
| Signature | 위변조 방지 서명       | 내부 정보 없음     |

---

## ✅ 실제 예시

```jwt
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
eyJzdWIiOiJ1c2VyMTIzIiwibmFtZSI6Iu2VnOychOy5mCIsImlhdCI6MTcxNzA1ODQwMCwiZXhwIjoxNzE3MDYyMDAwfQ.
9GnWTqlQOpDFKFGw3TmqH4g49j-LyNMaNcBHHpG_H0U
```

* 첫 번째 블록: Header (base64 디코딩하면 JSON 나옴)
* 두 번째 블록: Payload
* 세 번째 블록: Signature (서명 값)

---

### 정리

* **JWT는 인증 정보를 안전하게 전달하는 용도**지, **민감한 정보 저장용이 아님**
* Signature 덕분에 **위조를 방지**할 수 있음
* 하지만 **만료 시간 지나면 무효**, 그리고 탈취되면 위험하므로 보안에도 주의 필요


  </details>

</details>
