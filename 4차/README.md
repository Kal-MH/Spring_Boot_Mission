# Basic

로그인, 회원가입 화면을 구성하고, 해당 기능을 구현합니다.

## 구현

### 경로

- GET /home : 홈화면
- GET /user/login : 로그인 화면
- GET /user/signup : 회원가입 화면
- POST /user/signup : 회원가입
- POST /user/logout : 로그아웃

### View 추가

현재 프로젝트에는 홈화면, 로그인, 회원가입에 대한 view 템플릿이 추가되어 있습니다.

- index.html
- login-form.html
- signup-form.html

### 컨트롤러 경로 추가 및 UserService수정

추가할 경로에 대해서 아래와 같이 메서드를 추가해줍니다.

- 예) homeController
    
    ```java
    @Controller
    @RequestMapping("home")
    public class HomeController {
    
        @GetMapping
        public String home() {
            return "index";
        }
    }
    ```
    

또한, 현재 사용자 정보 중 거주지에 대한 정보를 설정하는 것은 고려되지 않기 때문에 아래 더미 데이터를 사용하여 임의로 설정합니다.

- 거주지 더미 데이터
    1. 서울시 서초구 서초동, 37.4877° N, 127.0174° E
    2. 서울시 강남구 역삼동, 37.4999° N, 127.0374° E
    3. 서울시 강남구 삼성동, 37.5140° N, 127.0565° E
- rotationValue를 설정하여 각각의 더미데이터가 순차적으로 저장됩니다.
    
    ```java
    private static long rotationValue;
    private static final int DUMMY_LENGTH = 3;
    
    ...
    
    @Service
    public class UserService {
    		public UserDto createUser(UserDto userDto){
    				//rotationValue에 맞춰 거주지 설정
            Optional<AreaEntity> areaEntityOptional = this.areaRepository.findById((rotationValue % DUMMY_LENGTH) + 1);
            ...
    
    				//rotationValue 초기화
            if (rotationValue == 100)
                rotationValue = 0;
            rotationValue++;
    
            return new UserDto(userEntity);
        }
    }
    
    ```
    

### 실패 상황 고려

로그인과 회원가입을 위해 입력하는 값은 사용자로부터 주어지기 때문에 여러 실패 상황들을 고려해야 합니다. 간단하게 생각해볼 수 있는 경우들은 다음과 같습니다.

- 회원가입
    - 빈 값
    - 패스워드 불일치
    - 중복 아이디
- 로그인
    - 빈 값
    - 아이디 존재하지 않음
    - 비밀번호 불일치

우선, 회원가입 실패 상황에 대해서는 `UserController`에서 처리되고 있습니다. 

회원가입 정보에 대해 클라이언트에서는 아무런 처리가 이뤄지지 않는다고 가정할 때, 위 3가지 상황에 대해서 화면에 에러메세지를 출력합니다.

```java
@Controller
@RequestMapping("user")
public class UserController {

	@PostMapping("/signup")
  public String signupUser(
          @RequestParam("username") String username,
          @RequestParam("password") String password,
          @RequestParam("password_check") String passwordCheck,
          @RequestParam(value = "is_shop_owner", required = false) boolean isShopOwner,
          Model model
  ) {
			//만약, 패스워드가 일치하지 않는다면, 에러 메세지 출력
      if (!password.equals(passwordCheck)) {
          logger.info("User Signup Error : Not Valid input value");
          model.addAttribute("username", username);
          model.addAttribute("error", "true");
          model.addAttribute("exception", "Password Not Equal");
          return "signup-form";
      }
			...
	}
}
```

로그인과 관련해서는 현재, 사용자 계정 정보를 다룰 수 있게 도와주는 `CustomUserDetailService` 를 통해 `loadUserByUsername` 함수에서 `UsernameNotFoundException` 처리가 이뤄지고 있습니다.

```java
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
    final UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(()->new UsernameNotFoundException("CustomUserDetailService Error : Cannot find username."));
    return new User(username, userEntity.getPassword(), new ArrayList<>());
}
```

로그인이 실패한 경우, 다음 동작을 살펴보면 아무런 메세지가 없이 다시 로그인 페이지가 렌더링되는 것을 확인할 수 있습니다. 좀 더 에러 상황을 구분해서 전달하기 위해 몇 가지 추가적인 처리를 진행합니다.

- `CustomeAuthFailureHandler` 클래스를 새로 작성합니다.
    
     `CustomeAuthFailureHandler` 클래스는 `SimpleUrlAuthenticationFailureHandler`를 상속받은 클래스로, 로그인 실패 시, `onAuthenticationFailure` 함수를 통해 에러 핸들링을 할 수 있게 도와줍니다.
    
    - `onAuthenticationFailure` 오버라이딩
        
        ```java
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        
            logger.info(exception + "");
            String errorMsg = "";
            if (exception instanceof UsernameNotFoundException)
                errorMsg = "Login Fail : Username Not Found.";
            else if (exception instanceof BadCredentialsException)
                errorMsg = "Login Fail : Invalid id, password";
           setDefaultFailureUrl("/user/login?error=true&exception=" + errorMsg);
            super.onAuthenticationFailure(request, response, exception);
        }
        ```
        
- `WebSecurityConfig` 설정 수정
    
    로그인 설정 블록에 `.failureHandler(this.authenticationFailureHandler)` 추가합니다.
    

## 실행화면

- 홈화면

![home](https://user-images.githubusercontent.com/59648372/159421016-75896ad3-0066-40bc-bd98-458412558ded.png)

- 로그인 화면

![login](https://user-images.githubusercontent.com/59648372/159421058-08059893-ac8a-4634-b5ae-b3b16d604301.png)

- 회원가입 화면

![signup](https://user-images.githubusercontent.com/59648372/159421140-67c3facc-f5dc-4749-952e-2e2959fc7356.png)

- 로그인 성공

![login-home](https://user-images.githubusercontent.com/59648372/159421184-c1b56a1f-a422-46ba-ad61-2657b5ed5e0a.png)

- 회원가입 중복 아이디

![duplicate](https://user-images.githubusercontent.com/59648372/159421186-def423b6-b250-4b0e-9e37-29007667c9dd.png)

- 로그인 실패

![loginfail-id](https://user-images.githubusercontent.com/59648372/159421181-892440d3-4bee-4f57-872f-1665a3242cec.png)

## 개선점

- 패스워드 일치, 불일치 검사, 빈 값 검사 등은 클라이언트단으로 옮기는 것이 더 좋습니다. 불필요한 서버 통신을 줄이므로 성능을 향상 시킬 수 있습니다.
- 에러 상황에 대해서 좀 더 명확한 처리가 필요합니다.
    
    로그인 과정에서 발생하는 실패 상황들은 다양합니다. 제시한 아이디, 패스워드 일치 및 계정 존재 여부 이외에도 인증 요청이 거부되거나, 내부 시스템으로 인한 요청 처리 불가 문제 등 
    다양한 상황에 대해서 처리할 수 있도록 추가적인 구현이 필요합니다.
    
    
    
    
