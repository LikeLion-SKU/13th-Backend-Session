# ⚙️SpringBoot에서 로그인 과정
1. ⃣클라이언트에서 로그인 API로 Post요청(데이터 포함) 보냄
2. SpringSecurity 및 내부 로직을 통해서 사용자 정보 확인
- 사용자 정보가 맞다면, JWT AccessToken 과 RefreshToken 발급
3. 토큰 발급 및 전달
- AccessToken (만료시간이 짧음)
- RefreshToken (만료시간이 길다)
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs..."
}
```
4. 클라이언트는 서버의 API로 요청을 보낼 때, 본인의 AccessToken을 포함하여 요청
5. 서버는 토큰 유효성 검사를 한 후 사용자 정보를 반환
6. 만약 AccessToken이 만료됐을 경우, 클라이언트는 RefreshToken을 사용하여 새로운 AccessToken을 요청
7. 서버는 Refresh토큰 유효성 검사를 한 후 새로운 AccessToken을 발급
8. 로그아웃시 RefreshToken을 DB에서 제거
## ✅AccessToken
역할 : 사용자가 로그인시 발급되는 수명 짧은 토큰 
사용처 : API 요청시 매번 사용
- 서버는 stateless하게 토큰만 확인하여 사용자 확인
```http request
Authorization: Bearer <AccessToken>
```
## ✅RefreshToken
역할 : AccessToken 만료시 새로운 AccessToken을 받기위한 토큰
사용처 : 로그인 상태 유지 (자동 로그인 등 )
- 서버 또는 DB에 저장되어야하며, 클라이언트가 보통 httpOnly 쿠키로 저장