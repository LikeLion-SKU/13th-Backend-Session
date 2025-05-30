# 9주차 과제

다중 선택: 매주 과제

<aside>
✏️

### JWT

JSON Web Token, JSON 형식의 데이터를 담은 토큰

- 사용자 인증 정보를 안전하게 주고받기 위한 토큰 기반 인증 방식

  → 사용자 정보를 안전하게 저장하고 서버를 Stateless하게 인증 처리 가능

### JWT 구성요소 (Header.Payload.Signature 형태)

- Header : 어떤 알고리즘으로 암호화하고 어떤 토큰을 사용할 것인지의 정보

    ```json
    {
      "alg": "HS256",   // 서명에 사용된 알고리즘 정
      "typ": "JWT"      // 토큰의 타입
    }
    ```


- Payload : 사용자 id와 같이 전달하려는 정보

  이 부분은 암호화를 하지 않기 때문에 민감한 데이터는 넣어서는 안됨

    ```json
    {
      "sub": "1234567890", // 누구에 대한 토큰인지 식별하는 고유 ID
      "name": "Sim", // 사용자 이름
      "admin": true, // 사용자가 관리자 권한을 가지고 있는지의 여부
      "iat": 1516239022 // 토큰 발행 시간 
    }
    ```


- Signature : 헤더와 정보를 합친 후 서버가 지정한 secret key로 암호화 시킨 것

  → 토큰이 발급된 시점을 기준으로 암호화 되어있기에 중간에 공격으로 변경이 된다면 공격 여부를 알 수 있다

    ```scss
    HMACSHA256( // 사용된 알고리즘
      base64UrlEncode(header) + "." + base64UrlEncode(payload),
      secret_key // 비밀 키
    )
    ```

### JWT 동작원리

사용자의 데이터 요청(JWT) → 서버에서 Access Token 검증 → 서버가 사용자에게 응답

</aside>

<aside>
✏️

### Access Token

사용자가 인증받았음을 보여주는 토큰으로 API를 호출할때 사용

- 유효기간이 짧다
- 보안수준이 낮다
- 클라이언트의 요청 마다 API 호출에 Access Token을 헤더에 담아서 보낸다
- 서버가 이 토큰을 디코딩해서 사용자 정보를 꺼낸다
- 설계 시 JWT에 민감한 정보를 담지 X, HTTPS로만 통신

### Refresh Token

Access Token 이 만료되면 새로운 Access Token을 발급 받기 위한 토큰

- 유효기간이 길다
- 보안수준이 높다
- 서버가 DB에 저장해서 유효성을 검증할 수 있도록 관리한다

  → DB의 User 테이블에는 항상 Refresh Token 을 넣을 필드가 있어야 함

**Access Token은 API를 호출할때 마다 전송해야하기에 노출 위험이 있음**

</aside>