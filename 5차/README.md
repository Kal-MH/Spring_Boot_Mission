# Basic

사용자 위치 정보 판단 기능 추가.

- 이전 4차 Basic 미션을 토대로 진행합니다.
    - `AreaEntity`, `CategoryEntity`, `ShopEntity`, `ShopReviewEntity`, `ShopPostEntity`, `UserEntity` 존재하고 각각의 repository, service, controller가 존재.
    - 로그인, 회원가입 기능 구현
    - 테스트를 위한 임의의 사용자 정보 추가
	    - `id` / `password`
			- test_user1 / test1pass
			- test_user2 / test2pass
			- test_user3 / test3pass
	    - 거주지 정보 등록
			- 서울시 서초구 서초동, 37.4877° N, 127.0174° E
			- 서울시 강남구 역삼동, 37.4999° N, 127.0374° E
			- 서울시 강남구 삼성동, 37.5140° N, 127.0565° E

## 구현

### View 파일 수정

사용자가 로그인한 사용자인 경우, home화면에 해당하는 `index.html` 파일에서 다음의 부분이 보여지게 됩니다.

```html
<div sec:authorize="isAuthenticated()">
    <h3>
        Welcome back, <span sec:authentication="name"></span>!
    </h3>
    <form th:action="@{/user/logout}" method="post">
        <input type="submit" th:value="로그아웃">
    </form>
</div>
```

여기에 사용자의 현재 위치 정보와 등록된 주소 정보 중에 가장 가까운 지역 정보를 받아 오는 버튼을 하나 추가합니다.

```html
<div>
    <button onclick="getLocation()">현재 위치 보기</button>
    <div id="location-result">
    </div>
</div>
```

`getLocation()` 함수의 기능은 2가지 입니다.

- 사용자의 현재 위치 정보 받아오기
- 서버에 저장되어 있는 지역 정보 중에서 사용자의 현재 위치에서 가장 가까운 지역 정보 받아오기.

사용자의 현재 위치 정보를 받아오기 위해서 브라우저의 다양한 정보를 제공해주는 javaScript의 `navigator` 객체를 사용합니다. `navigator`는 네트워크 연결 정보, 브라우저의 UI언어 등의 정보들을 반환해주는 메소드를 갖고 있으면 `.geolocation()`는 장치의 위치 정보에 접근할 수 있는 `Geolocation`객체를 반환합니다. [[출처 - MDN Navigator]](https://developer.mozilla.org/ko/docs/Web/API/Navigator)

`index.html` 파일 내에 각각 `getLocation()` 함수와 `showPosition()` 함수를 정의합니다. 또한, `showPosition()` 함수는 `navigator` 객체를 이용해서 얻은 위치 정보를 `GET` 메소드를 통해 서버에 보내고 현재 위치 기준, 가장 가까운 등록된 지역 정보를 응답으로 보냅니다. 

```jsx
async function showPosition(position) {
	...
	let response = await (await fetch(`/area/get-location-info?latitude=${latitude}&longitude=${longitude}`, {
	    method : "GET",
	})).json();
	...
}
```

### AreaController URI추가

위치 정보를 얻기 위한 URI는 다음과 같습니다.

```
/area/get-location-info?latitude=${latitude}&longitude=${longitude}
```

`Areacontroller`에서는 각각 `latitude`, `longitude`를 `RequestParam`으로 받고 `AreaDto` 형식의 위치 정보를 반환합니다.

```java
@GetMapping("get-location-info")
public ResponseEntity<AreaDto> getLocationInfo(
        @RequestParam("latitude") Double latitude,
        @RequestParam("longitude") Double longitude
) {
    return ResponseEntity.ok(this.areaService.getLocationInfo(latitude, longitude));
}
```

### AreaService 로직 추가

현재 위치에서 가장 가까운 지역 정보를 얻기 위해서 두 점 사이 거리 구하는 방식을 사용합니다. 거리 구하는 계산은 `AreaRepository`를 통해 가져온 모든 `AreaEntity`를 대상으로 이뤄집니다.

```java
public AreaDto getLocationInfo(Double latitude, Double longitude) {
    double minDistance = Long.MAX_VALUE;
    AreaEntity minAreaEntity = null;
    Iterable<AreaEntity> iterable = this.areaRepository.findAll();
    Iterator iterator = iterable.iterator();

    while (iterator.hasNext()) {
        AreaEntity areaEntity = (AreaEntity) iterator.next();
        double xDistance = Math.pow(Math.abs(latitude - areaEntity.getLatitude()), 2);
        double yDistance = Math.pow(Math.abs(longitude - areaEntity.getLongitude()), 2);
        if (minDistance > Math.sqrt(xDistance + yDistance)) {
            minDistance = Math.sqrt(xDistance + yDistance);
            minAreaEntity = areaEntity;
        }
    }
    logger.info("AreaService getLocationInfo : " + minAreaEntity);
    return new AreaDto(minAreaEntity);
}
```

## 실행 화면

![basic](https://user-images.githubusercontent.com/59648372/161682927-eb654231-7a02-4ebf-9185-10f56fb79768.png)


# Challenge

아래 Challenge에 관한 README는 5차 mission 진행 과정을 기록하기 위함입니다. 5차 Challenge 미션은 모두 구현하지 못하였으므로
Basic으로 제출이 되었습니다.

challenge 기능 확인을 위해서 임의 사용자를 미리 등록합니다.

- `id` / `password` : entity_user / test1pass

## 구현 1

- `auth-sso` 에서 Redis를 이용해 로그인 정보를 저장합시다.
    1. 로그인에 성공하였을때, 사용자의 정보를 Redis에 적재합니다.
    2. `SsoCookieHandler` 의 `onAuthenticationSuccess` 함수를 활용합니다.
    3. `onAuthenticationSuccess` 에서 저장하는 Cookie의 값을, 임의의 UUID로 전환하여 저장합시다.

### 구현 과정

1. `RedisConfig` 생성 및 redis 설정
2. `UserInfo` 클래스 생성 및 `RedisRepository`, `RedisService` 작성
    
    `UserInfo` 클래스는 Redis 에 저장되는 객체 단위 클래스를 의미합니다.
    
    ```java
    @RedisHash("User")
    public class UserInfo implements Serializable {
        private String id;
        private String username;
        private int status;
    		...
    }
    ```
    
3. `SsoCookieHandler` 수정
    
    ```java
    @Override
        public void onAuthenticationSuccess(
                HttpServletRequest request,
                HttpServletResponse response,
                Authentication authentication
        ) throws IOException, ServletException {
            logger.info("onAuthenticationSuccess, create new cookie");
    
            //Generate UUID
            String userId = UUID.randomUUID().toString();
            String username = authentication.getName();
            //save UserId in Redis
            try {
                this.redisService.saveUserInfo(userId, username);
                Cookie loginCookie = new Cookie(
                        "likelion_login_cookie",
                        userId
                );
                ..
                super.onAuthenticationSuccess(request, response, authentication);
            } catch (InterruptedException e) {
               ...
        }
    ```
    
    `UUID` 객체를 통해 랜덤한 식별자를 생성하고, `redisService`를 통해서 값을 저장한다.
    
    - `redisService()` : 식별자와 `username`을 인자로 받아서 redis에 `UserInfo` 클래스 객체로 저장합니다.

## 구현 2

- `SsoLoginController` 에 현재 로그인한 사용자의 정보를 반환하는 `RequestMapping` 을 추가합시다.
    1. SSO를 활용하여 로그인을 하는 서비스 (community 같은)가 현 사용자의 정보를 확인하기 위한 `RequestMapping` 입니다.
    2. 사용자의 `Cookie`에 저장된 `likelion_login_cookie` 의 값을 인자로 받으면, Redis에 저장해둔 사용자 정보를 반환합니다.
    3. community의 `SsoAuthFilter` 에서, `WebClient` 를 활용하여 auth-sso로 요청을 보내어 확인하도록 요청합니다.

### 구현 과정

**auth-sso**

1. `SsoLoginController`에 로그인한 사용자 정보를 반환하는 `RequesetMapping` 추가.
    
    ```java
    @GetMapping("request-userinfo")
        public @ResponseBody UserInfo requestUserInfo (
                HttpServletRequest request,
                @RequestParam("token_value") String token_value
        ) {
            logger.info("SsoController request user info : {}", token_value);
            return this.redisService.retrieveUserInfo(token_value);
        }
    ```
    
    클라이언트로부터 `/request-userinfo?token_value={user_token}` 값이 전달되면, `redisService`를 이용해서 현재 저장되어 있는 로그인한 사용자인 지 확인하고 값을 보내줍니다.
    
    `RedisService`에서 `retrieveUserInfo()` 함수는 다음과 같이 구현되어 있습니다.
    
    ```java
    public UserInfo retrieveUserInfo(String userId) {
            logger.info("RedisService retrieveUserInfo userId : {}", userId);
            Optional<UserInfo> userInfoOptional = this.redisRepository.findById(userId);
            if (userInfoOptional.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            return userInfoOptional.get();
        }
    ```
    
    `Optional` 객체를 사용하여 만약, 저장되어 있지 않다면(로그인된 사용자가 아니라면) `NOT_FOUND` 에 해당하는 상태를 전달하게 됩니다.
    

**community**

1. `WebClient` 설정 추가
    
    `auth-sso` 서버로 요청을 보내기 위해서 `WebClient`를 사용합니다.
    
    ```java
    @Bean
        public WebClient authClient() {
            return WebClient.create("http://localhost:8000");
        }
    ```
    
    또한, `WebClient` 객체를 이용해서 실제 요청을 보내는 `AuthService` 도 작성합니다.
    
    ```java
    public UserInfoDto getUserInfo(String tokenValue) {
        UserInfoDto userInfoDto = this.authClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path("/request-userinfo")
                                .queryParam("token_value", tokenValue)
                                .build()
                )
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    logger.error(clientResponse.statusCode().toString());
                    return Mono.empty();
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                        Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)))
                .bodyToMono(UserInfoDto.class)
                .block();
        return userInfoDto;
    }
    ```
    
    앞서 작성한 `RequestMapping`을 만들기 위해 `uriBuilder`를 사용합니다. 요청을 보낸 후, 응답 결과에 대해 400번, 500번 상태코드가 돌아온 경우를 대비하여 에러 처리 과정을 추가합니다. 정상적인 값으로 돌아온 경우, `UserInfoDto` 형태로 받습니다.
    
2. `SsoAuthFilter` 수정
    
    `community` 서버에서 요청이 일어나기 전, Filter단에서부터 `auth-sso` 서버를 통해 현재 요청하는 사용자가 인증된 사용자인 지 확인하는 과정을 갖습니다.
    
    ```java
    Optional<String> authToken = authTokenFromCookie(httpRequest.getCookies());
    if (authToken.isEmpty()) {
        authToken = authTokenFromQuery(httpRequest, httpResponse);
    }
    
    if (authToken.isPresent()) {
        logger.info("Login Token Value: {}", authToken.get());
        this.setAuthentication(httpRequest, httpResponse, authToken.get());
    } else {
        logger.info("Login Token Missing");
        this.setAuthentication(httpRequest, httpResponse, "anonymous");
    }
    ```
    
    - 현재 쿠키값을 확인하고 `auth-sso` 서버로 요청을 보냅니다.
        - 현재 쿠키값이 존재하지 않다면 인증된 사용자가 아니므로 `anonymous`로 진행합니다.
        - 만약, 쿠키값이 존재한다면, 해당 값으로 요청을 보냅니다.
    - `auth-sso` 서버로부터 온 응답 결과에 따라서 현재 사용자가 로그인한 상태인지 판단합니다.
        
        ```java
        private void setAuthentication(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String tokenValue){
                // 1. request userInfo with WebClient
                UserInfoDto userInfoDto = this.authService.getUserInfo(tokenValue);
                // 2. set Authentication based on token
                if (userInfoDto != null) {
                    logger.info("Login UserInfo: {}", userInfoDto.getUsername());
                    this.setLoginAuthentication();
                }
                else {
                    logger.info("Anonymous");
                    this.setAnonymousAuthentication();
                }
        
            }
        ```

## 실행 화면

![로그인 성공](https://user-images.githubusercontent.com/59648372/161716967-0cf3f529-e32d-4bc3-865c-d88df9b4946d.png)

# 개선점


- Publish - Subscribe 패턴을 활용하여 SSO서버로부터 로그아웃하는 과정을 구현하지 못했습니다.
- filter단에서 사용자 로그인 상태를 확인하는 구현 과정이 쉽지 않았습니다.
    - `WebClient`를 통해서 요청했지만, `auth-sso` 서버로 요청되지 않는 상황이 발생했었습니다. `uri` 포맷(`uriBuilder` 활용)하는 방법과 `auth-sso` 서버에서 `WebSecurityConfig` 부분에 대한 수정이 필요함을 알 수 있었습니다.
    - 현재, 1차적으로 `community` 서버에서 쿠키를 확인한 다음, `auth-sso` 서버에 사용자 정보 요청을 진행하고 있습니다. 해당 구현 로직이 실제 알맞은 방법인지 알 수 없습니다.

