<details>
  
  <summary>6주차 미션</summary>

  <details>
    <summary>정리</summary>

## 스프링 시큐리티란?
스프링 시큐리티는 스프링 기반의 애플리케이션 보안(인증, 인가, 권한)을 담당하는 **스프링 하위 프레임워크**입니다. 보안 옵션을 많이 제공해주고 복잡한 로직 없이도 어노테이션으로도 설정이 가능합니다. 여러 보안 위협 방어 및 요청 헤더도 보안처리를 해줍니다. 기본적으로 스프링 시큐리티는 **세션 기반 인증**을 제공합니다.

## 인증(Authentication)
인증은 사용자의 신원을 입증하는 과정입니다. 쉽게 말하면 어떤 사이트에 아이디와 비밀번호를 입력하여 로그인 하는 과정입니다.

## 인가(Authorization)
**'권한부여'나 '허가'와 같은 의미로 사용됩니다.** 즉, 어떤 대상이 **특정 목적을 실현하도록 허용(Access)** 하는 것을 의미합니다. 예를 들면, 파일 공유 시스템에서 권한별로 접근할 수 있는 폴더가 상이합니다. 관리자는 접속이 가능하지만 일반 사용자는 접속할 수 없는 경우에서 사용자의 권한을 확인하게 되는데, 이 과정을 인가라고 합니다.

**`스프링 시큐리티는 필터 기반으로 동작합니다.`**

## Security 설정을 위해 알아야 할 용어
### HttpServletRequest
클라이언트(브라우저 등)의 HTTP 요청 정보를 서버에서 가져올 수 있도록 해주는 객체.
클라이언트가 서버로 요청할 때 담긴 모든 요청 정보를 포함하고 있음

### Filter
HTTP 요청/응답이 서블릿에 도달하기 전 또는 응답이 클라이언트에게 전달되기 전에 중간에서 가로채 처리하는 구성 요소

### Manager
어떤 리소스나 동작을 관리하는 역할

### Provider
소프트웨어 아키텍처에서 필요한 것을 실제로 제공해주는 역할
실질적인 기능을 수행하거나 자원을 제공함

### Handler
특정 종류의 요청이나 이벤트를 처리하는 담당자
어떤 일이 발생했을 때 직접 그 작업을 수행하는 객체

### Token
인증/인가, 또는 세션 상태를 클라이언트에 저장하기 위해 사용되는 데이터 조각, 
서버가 클라이언트에게 권한이 있음을 증명하기 위해 발급하며, 
토큰을 통해 클라이언트는 매번 인증 정보를 보내지 않고도 요청을 수행 가능

## Spring Security Architecture
![image](https://github.com/user-attachments/assets/211af5dc-237b-4de2-9d52-7f7e898debf6)
1. 사용자가 아이디와 패스워드 입력 시 HTTPServletRequest에 아이디와 비밀번호 정보를 전달, 
 AuthenticationFilter가 넘어온 아이디와 비밀번호의 유효성 검사 진행
2. 유효성 검사가 끝나고 실체 구현체인 UsernamePasswordAuthenticationToken을 만들어 넘겨줌
3. 전달 받은 인증용 객체인 UsernamePasswordAuthenticationToken을 AuthenticationManager에게 보냄
4. AuthenticationManager는 등록된 AuthenticationProvider 중 하나에 이 요청을 위임 
 (UsernamePasswordAuthenticationToken을 AuthenticationProvider에게 보냄)
5. AuthenticationProvider는 사용자 정보를 조회하기 위해 사용자 아이디를 UserDetailsService로  
보내면, 사용자 아이디로 찾은 사용자 정보를 UserDetails 객체로 만듦
6. 만든 UserDetails 객체를 통해 DB에 있는 사용자 정보를 가져옴
7. 입력된 정보와 UserDetails의 정보를 비교해 실제 인증 처리를 진행
8.
9. AuthenticationManager, AuthenticationFilter를 거쳐 인증 처리 진행
10. 인증이 완료되면 SecurityContextHolder에 Authentication을 저장,
    인증 성공 여부에 따라 성공하면 AuthenticationSuccessHandler, 실패하면 AuthenticationFailureHandler를 실행

## 로그인 인증 처리 절차 요약
사용자가 로그인 요청 -> HTTPServletRequest에 아이디와 비밀번호가 담겨서 전달 됨 
-> 인증 Token을 통해 DB에 잇는 사용자 정보를 가져옴 -> DB에 있는 사용자 정보와 사용자가 요청한 로그인 정보가 일치할 경우 Success, 불일치할 경우 Failure

### Security 설정을 위해 알아야 할 메소드
프로젝트의 목적에 따라 더 다양한 설정을 할 수 있지만, 항상 사용하는 메소드
- requestMachers(): 특정 요청과 일치하는 url에 대한 액세스를 설정
- permitAll(): 누구나 접근이 가능하게 설정(인증/인가 없이)
- anyRequest(): permitAll 이외의 url에 대한 요청 설정
- authenticated(): 별도의 인가는 필요하지 않지만 인증이 성공된 상태여야 접근 가능
- BCryptPasswordEncoder(): 패스워드 인코더를 bean으로 등록

## CSRF란?
Cross-Site Request Forgery, 크로스 사이트 요청 위조
**공격자가 사용자의 권한을 빌려서, 사용자가 의도하지 않은 요청을 서버로 보내게 하는 공격 기법**

- 사용자가 은행 웹사이트에 로그인한 상태에서, 공격자가 사용자에게 악성 이메일을 보내거나 가짜 웹사이트로 유도
- 웹사이트 접속 시 공격자는 사용자가 모르게 "돈을 이체하는" HTTP 요청을 은행 사이트에 보냄 
이때 은행 사이트는 이미 사용자가 로그인한 상태이므로, 해당 요청이 유효하다고 판단하고 돈을 이체할 수 있게 됨

### Spring에서의 CSRF 설정
**Spring Security에서는 기본적으로 CSRF 보호가 활성화되어 있으며, REST API를 이용한 서버이면 session 기반 인증과는 다르게 stateless이기 때문에 서버에 인증 정보를 보관하지 않으므로 불필요한 CSRF 공격 방지를 할 필요가 없음**

## CORS란?
Cross-Origin Resource Sharing, 교차 출처 리소스 공유
서로 다른 도메인 또는 서버 간에 자원을 요청하고 받을 때 발생하는 보안 제약을 해결하기 위한 방법

- 웹 애플리케이션에서 한 도메인(예: example.com)에서 다른 도메인(예: api.example.com)에 HTTP 요청을 보낼 때,  
기본적으로 브라우저는 보안상의 이유로 이러한 요청을 제한한다. 이는 Cross-Origin(교차 출처) 요청으로 간주되기 때문인데,  이런 제한을 CORS 정책을 통해 해결할 수 있다.

### Spring에서의 CORS 동작 방식
- **브라우저의 기본 동작: 웹 애플리케이션에서 다른 도메인으로 API 호출을 할 경우, 브라우저는 이를 "교차 출처 요청”으로 간주하고, 해당 요청을 서버에 보냄**
- **서버의 응답: 서버는 Access-Control-Allow-Origin 헤더와 같은 CORS 관련 헤더를 포함하여 클라이언트에게 응답하고, 이 헤더는 서버가 요청을 허용할 출처(Origin)를 명시함**

### CORS의 주요 Header
- **Access-Control-Allow-Origin : 어떤 출처(origin)에서 요청을 허용할지 지정**
- **Access-Control-Allow-Methods : 어떤 HTTP 메서드(GET, POST, PUT, DELETE 등)를 허용할지 지정**
- **Access-Control-Allow-Headers : 클라이언트가 요청 시 사용할 수 있는 헤더들을 정의**
- Access-Control-Allow-Credentials :  true로 설정하면, 자격 증명(쿠키, HTTP 인증 등)을 포함한 요청을 허용
- Access-Control-Expose-Headers : 클라이언트가 응답에서 접근할 수 있는 헤더를 지정
- Access-Control-Max-Age : 브라우저가 CORS 응답을 얼마나 오랫동안 캐시할지를 지정

## EC2(Elastic Compute Cloud)란?
`**아마존 웹 서비스(AWS)에서 제공하는 클라우드 컴퓨팅 서비스**`
- 클라우드 컴퓨팅은 인터넷(클라우드)을 통해 서버, 스토리지, 데이터베이스 등의 컴퓨팅 서비스를 제공 
 → **AWS에서 원격으로 제어할 수 있는 가상의 컴퓨터를 한 대 빌리는 것**
- 후불제 PC방과 같이 사용한 만큼 비용을 지불하기 때문에 탄력적인 이라는 의미의 Elastic이라는 단어가 붙음 
 → **Elastic은 비용적인 부분 뿐만이 아니라 필요에 따라 성능, 용량을 자유롭게 조절할 수 있다는 의미도 가지고 있음**
</details>
<details>
    <summary>Access Token, Refresh Token</summary>

Access Token(엑세스 토큰)과 Refresh Token(리프레시 토큰)은 **사용자 인증과 권한 부여를 위해 사용하는 토큰 기반 인증 시스템의 핵심 요소**입니다. 특히 OAuth2, JWT 기반 시스템에서 많이 쓰입니다.

---

## ✅ 개념 요약

| 구분     | Access Token          | Refresh Token           |
| ------ | --------------------- | ----------------------- |
| 목적     | 리소스 서버 접근 (API 요청 인증) | Access Token 재발급        |
| 수명     | 짧음 (예: 15분\~1시간)      | 김 (예: 1주\~1달)           |
| 저장 위치  | 클라이언트(브라우저, 앱 등)      | 클라이언트 (보안 강화 필요)        |
| 보안 중요도 | 높음                    | 매우 높음 (탈취 시 장기적인 피해 가능) |
| 사용 대상  | API 서버                | 인증 서버                   |

---

## ✅ 동작 흐름

1. **로그인 시도**

   * 사용자 로그인 → 서버에서 `Access Token`과 `Refresh Token` 발급

2. **Access Token 사용**

   * 클라이언트는 매 API 요청마다 `Access Token`을 HTTP Header에 담아서 전송
   * 서버는 토큰 유효성 확인 후 요청 처리

3. **Access Token 만료**

   * 만료되면 클라이언트는 `Refresh Token`을 이용해서 새로운 `Access Token` 요청

4. **Refresh Token 검증**

   * 서버가 `Refresh Token` 유효하면 새 `Access Token` 재발급

5. **Refresh Token 만료/탈취**

   * 재로그인 필요

---

## ✅ 예시 (JWT 기반)

```json
// Access Token 예시 (15분 유효)
{
  "sub": "user123",
  "exp": 1715256100,
  "role": "USER"
}
```
- role이 있음 → 사용자의 권한을 판단할 수 있는 정보
- 이 정보는 API 접근 허용 여부 판단에 쓰임 → Access Token
```
// Refresh Token 예시 (7일 유효)
{
  "sub": "user123",
  "exp": 1715860900
}
```
- 단순히 sub와 exp만 있음
- 권한 정보 (role) 없음
→ Access Token을 재발급받기 위한 식별용 → Refresh Token

---

## ✅ 왜 둘 다 필요할까?

* **Access Token만 쓰면?**

  * 만료 시간을 길게 설정해야 하므로 보안에 취약해짐
  * 탈취되면 오랫동안 악용 가능

* **Refresh Token을 함께 쓰면?**

  * Access Token은 짧게 설정하여 보안을 강화
  * 사용자가 매번 로그인할 필요 없이, Refresh Token으로 자동 연장 가능

---

## ✅ 실무 팁

* Access Token은 주로 **HTTP Header의 Authorization**에 `Bearer {token}` 형식으로 전달
* Refresh Token은 **보안상 쿠키에 HttpOnly 속성으로 저장**하는 경우가 많음
* Refresh Token 유출 시 심각한 보안 사고로 이어질 수 있으므로, 별도로 블랙리스트 관리하거나 저장소(예: Redis)에 기록하기도 함

---

# 정리
## ✅ 실제 서비스에선 이렇게 동작 (자동 재발급)
1. 사용자가 API 요청 시 Access Token을 보냄
2. Access Token이 유효 → 정상 응답
3. Access Token이 만료됨 → 서버가 401 Unauthorized 응답
4. 클라이언트(브라우저, 앱)는 Refresh Token을 자동으로 사용해 새로운 Access Token 요청
5. 새 Access Token 발급 → 요청 다시 전송

**📌 즉, 사용자는 재로그인할 필요 없이 재발급 과정을 느끼지 못함**
| 이유             | 설명                              |
| -------------- | ------------------------------- |
| 🔐 보안          | Access Token이 짧을수록 탈취돼도 피해가 적음  |
| ⚙️ 자동화         | Refresh Token으로 백그라운드에서 자동으로 갱신 |
| 🚫 사용자는 인식 못 함 | 대부분 앱/웹에서는 로그인 유지처럼 보임          |


</details>
</details>
