# Basic

## Spring Properties
- 각각 local, test모드로 나눠 개발을 진행합니다.
    - application-test.yml
        - h2 database 사용
    - application-local.yml
        - MySQL8 사용
- DB키 보호를 위해 application-local.yml은 .gitignore로 관리합니다.

## Entity

### BaseEntity

기본적으로 모든 `Entity`들은 `BaseEntity`를 상속 받습니다.

- createAt : 생성 시각
- updateAt : 마지막 수정 시각

### BoardEntity

```java
- id : Primary Key
- name
- postEntityList @OneToMany
```

- 게시판과 게시글은 @OneToMany 관계에 있습니다.

### PostEntity

```java
- id : Primary Key
- title
- content
- password
- boardEntity @ManyToOne
- writer(UserEntity) @ManyToOne
```

- 게시글은 게시판과 @ManyToOne 관계에 있습니다.
- 게시글은 사용자와 @ManyToOne 관계에 있습니다.

### UserEntity

```java
- id : Primary Key
- loginId
- postEntityList @OneToMany
```

- 사용자와 게시글은 @OneToMany 관계에 있습니다.

## API

기본적으로 BoardEntity, PostEntity, UserEntity에 대해서 다음과 같이 CRUD 기능을 지원합니다.

- BoardEntity(/board)
    - POST, GET, UPDATE, DELETE
    - ex) GET /board
    - 게시판 삭제 시, 게시판에 속한 게시글도 삭제됩니다.
- PostEntity(/post)
    - GET, UPDATE, DELETE
    - ex) GET /post/0
    - 게시글의 경우, 게시판에 무조건 소속되기 때문에 단독으로 생성되지 않습니다.
- UserEntity(/user)
    - POST, GET, UPDATE, DELETE
    - ex) POST /user
    - 사용자 삭제 시, 사용자가 작성한 게시글도 삭제됩니다.

또한, 게시판, 게시글, 사용자와의 관계를 고려해서 다음과 같은 기능을 사용할 수 있습니다.

- POST /board/{board-id}/post
    - 게시글 생성
- GET /board/{board-id}/post
    - board-id 게시글의 게시글 전체 조회
- GET /board/{board-id}/post/{post-idx}
    - board-id 게시글의 post-idx번째 게시글 상세 조회
- UPDATE  /board/{board-id}/post/{post-idx}
    - board-id 게시글의 post-idx번째 게시글 수정
- DELETE /board/{board-id}/post/{post-idx}
    - board-id 게시글의 post-idx번째 게시글 삭제

## 실행화면
- GET /board/1/post/

![boardPostread](https://user-images.githubusercontent.com/59648372/157107839-c659b558-e4c8-46f7-8c36-118dd6d0ecdd.png)

- GET /board/1/post/0

![boardPostreadOne](https://user-images.githubusercontent.com/59648372/157107886-8645e55d-fadd-4aa4-a814-68cb6309f524.png)

- GET /user

![userFindAll](https://user-images.githubusercontent.com/59648372/157108033-15474207-b893-4409-b979-7ef23cb89121.png)

- DELETE /user/1

(삭제되기 전)

![before](https://user-images.githubusercontent.com/59648372/157108042-fb5b372e-8f50-4dbc-8888-fb6f2bb302d0.png)

(삭제된 후)

![after](https://user-images.githubusercontent.com/59648372/157108048-52eaa24d-3d5e-403e-8c37-4f23cf48e097.png)

## 개선점
- cascade 사용과 오류 발생
    - 현재, UserEntity와 PostEntity, 그리고 BoardEntity와 PostEntity는 서로 관계를 맺고 있습니다.
    - 단순히, UserEntity와 BoardEntity에서 cascade설정을 추가함으로 사용자와 게시판이 삭제될 때, 연관된 게시글이 모두 삭제되도록 구현했으나, /board/{board-id}/post/{post-idx} 요청으로 들어온 delete가 제대로 작동하지 않았고, 결국에는 casecade 설정때문이었다는 것을 알게되었습니다.
    - DB 테이블간의 관계 설정을 더 잘 설계해야 함을 알 수 있었습니다.
        - casecade 사용조건
            - 등록, 삭제 등의 라이프사이클이 동일할 때
            - 단일 엔티티에서 완전 종속의 관계에 있을 때

# 

# Challenge

## Entity

(ERD Diagram)

![challenge ERD](https://user-images.githubusercontent.com/59648372/157195113-8b5b0fe3-ee33-42fa-86ef-0248fe771211.png)


### BaseEntity

`Entity`들은 `BaseEntity`를 상속 받습니다.

- `createAt` : 생성 시각
- `updateAt` : 마지막 수정 시각

### AreaEntity

- `AreaEntity`는 `Province`, `City`, `Street`, `LocationData` 타입의 클래스를 멤버 변수로 갖습니다.
    
    ```java
    AreaEntity
    - id : Primary Key
    - province : 도, 광역시 정보
    - city : 시, 군, 구
    - street : 동, 면, 읍
    - location : 위도, 경도
    ```
    
- `Province`, `City`, `Street`, `LocationData`는 사용자 정의의 새로운 타입으로 임베디드 타입(embedded type)입니다.
    - `@Embedded` : 값 타입을 정의하는 곳에 표시
    - `@Embeddable` : 값 타입을 사용하는 곳에 표시
    
    임베디드 타입은 `AreaEntity` 클래스가 지역 분류에 따른 새로운 타입의 데이터를 갖고 있을 수 있게 함으로써 상세 데이터를 그대로 가지고 있을 때보다 정보의 응집력을 높여주고 의미를 명확하게 합니다.
    
- `Province`, `City`, `Street`는 각각 `name` 이라는 중복된 멤버 변수를 갖고 있기 때문에 컬럼명을 재정의 할 필요가 있습니다.
    
    ```java
    @Embeddable
    public class Province {
        private String name;
    		...
    }
    ---
    @Embeddable
    public class City {
        private String name;
    		...
    }
    ```
    
    - `@AttributeOverride` 사용, 속성 재정의

### UserEntity

Basic 미션에서의 `UserEntity`를 기반으로 다음의 항목이 추가됩니다.

```java
UserEntity
- area : 사용자 거주지 (AreaEntity)
- shopEntityList : 소유하고 있는 상점 목록 (ShopEntity)
- isOwner : 사용자가 상점주인인지 나타내는 Onset값(boolean)
```

- `UserEntity`와 `AreaEntity`는 @OneToOne관계를 갖습니다.
- `UserEntity`와 `shoptEntity`는 @OneToMany 관계를 갖습니다.
- `isOwner`는 `Shop`이 만들어질 때, `Shop`의 생성자 안에서 변경됩니다.

### ShopEntity

`ShopEntity`와 연관된 Entity들은 다음과 같습니다.

- `CategoryEntity`
    
    ```java
    CategoryEntity
    - id : Primary Key
    - name
    ```
    
- `ShopPostEntity`
    
    ```java
    ShopPostEntity
    - id : Primary Key
    - title
    - content
    - password
    - shop (ShopEntity)
    - writer (UserEntity)
    ```
    
    - `ShopPostEntity` 와 `ShopEntity`는 @ManyToOne관계를 갖습니다.
        - `ShopPostEntity`는 가게 주인이 쓰는 게시글로, 하나 이상의 가게에 대한 글이 됩니다.
        - 가게 주인이 여러 군데에 가게를 가질 수 있기 때문입니다.
    - `ShopPostEntity` 와 `UserEntity`는 @OneToOne 관계를 갖습니다.
        - 가게 주인만이 쓸 수 있는 글(가게 주인은 한 명이라고 정합니다)로, `ShopPostEntity`에서 `checkIsOwner()` 메소드를 통해 현재 작성자가 가게 주인인지 확인합니다.
- `ShopReviewEntity`
    
    ```java
    ShopReviewEntity
    - id : Primary Key
    - title
    - content
    - password
    - shop (ShopEntity)
    - writer (UserEntity)
    ```
    
    - `ShopReviewEntity` 와 `ShopEntity`는 @ManyToOne관계를 갖습니다.
        - `ShopReviewEntity`는 일반 사용자가 쓰는 게시글로, 하나 이상의 가게에 대한 글이 됩니다.
    - `ShopReviewEntity` 와 `UserEntity`는 @OneToOne 관계를 갖습니다.
        - 일반 사용자 모두 쓸 수 있습니다.

`shopEntity`는 다음과 같습니다.

```java
ShopEntity
- id : Primary Key
- name : 가게 이름
- categoryEntityList : 취급하는 품목 (CategoryEntity)
- area : 가게 위치 (AreaEntity)
- owner : 가게 주인 (UserEntity)
- shopPostEntityList : 포스팅 글 리스트 (ShopPostEntity)
- shopReviewEntityList : 리뷰 글 리스트 (ShopReviewEntity)
```

- `shoptEntity`는 `CategoryEntity`와 @OneToMany관계를 갖습니다.
    - 하나의 상점은 여러 종류의 물품을 취급할 수 있습니다.
- `shoptEntity`는 `AreaEntity`와 @OneToOne 관계를 갖습니다.
- `shoptEntity`는 `UserEntity`와 @OneToOne 관계를 갖습니다.
- `shoptEntity`는 `ShopPostEntity`와 @OnetoMany 관계를 갖습니다.
- `shoptEntity`는 `ShopReviewEntity`와 @OnetoMany 관계를 갖습니다.

## API 설계 구상

1. `CategoryEntity`의 값은 기본적으로 채워진다고 상황을 가정합니다.
    1. API요청 하지 않음
    2. 기본적으로 ‘Food’, ‘Fashion’, ‘Living’ 과 같이 여러 품목 종류의 값이 들어가 있습니다.
2. `UserEntity`
    1. **POST /user**
        1. Request Body : `loginId`, `area`
    2. GET, UPDATE, DELETE 기본적으로 Basic과 동일하게 작용합니다.
        1. UPDATE같은 경우, Request Body를 통해 `loginId`, `area` 정보가 변경될 수 있습니다.
        2. DELETE 같은 경우, 연관 관계에 있는 `ShopEntity`, `ShopPostEntity`, `ShopReviewEntity` 레코드들이 삭제될 수 있습니다.
3. `ShopEntity`
    1. **POST /user/{id}/shop**
        1. 가게 주인 `id`에 대해서 `shop`을 하나 생성합니다.
        2. Request Body : `name`, `area`, `owner`
    2. **GET /shop** : 상점 전체 조회
    3. **GET /shop/{id}** : id값의 상점 조회
    4. **PUT /shop/{id}**
        1. Request Body : `name`, `area`, `owner`
        2. 고려사항
            1. `area` 변경 시, 기존 `area` 삭제 여부
            2. `owner` 변경 시, 기존 `owner`의 `isOnset`값의 변경 여부
    5. **DELETE /shop/{id}**
        1. 고려사항
            1. `area` 삭제 여부
            2. `shopPostEntityList`, `shopReviewEntityList` 삭제 여부
4. `ShopPostEntity`
    1. **POST /user/{user-id}/shop/{shop-id}/post**
        1. Request Body : `title`, `content`, `password`, `shop`, `writer`
        2. 작성 전에 `writer`가 `owner`와 일치하는 지 확인하는 작업이 필요합니다.
    2. **Get /shop/{shop-id}/post**
        1. shop-id를 가진 상점에 대한 `ShopPostEntity` 게시글 전체 조회
    3. **Get /shop/{shop-id}/post/{id}**
        1. shop-id를 가진 상점에 대한 `ShopPostEntity` id 게시글 상세 조회
    4. **PUT /shop/{shop-id}/post/{id}**
        1. Request Body : `title`, `content`, `password`
        2. shop-id를 가진 상점에 대한 `ShopPostEntity` id 게시글 수정
        3. `writer`, `shop`은 변경 대상이 아니라고 가정합니다.
        4. 고려사항
            1. `password` 일치 여부 확인 작업 필요.
    5. **DELETE /shop/{shop-id}/post/{id}**
        1. Request Body : `password`
        2. shop-id를 가진 상점에 대한 `ShopPostEntity` id 게시글 삭제
        3. 고려사항
            1. `password` 일치 여부 확인 작업 필요.
5. `ShopReviewEntity`
    1. **POST /user/{user-id}/shop/{shop-id}/review**
        1. Request Body : `title`, `content`, `password`, `shop`, `writer`
    2. **Get /shop/{shop-id}/review**
        1. shop-id를 가진 상점에 대한 `ShopReviewEntity` 게시글 전체 조회
    3. **Get /shop/{shop-id}/review/{id}**
        1. shop-id를 가진 상점에 대한 `ShopReviewEntity` id 게시글 전체 조회
    4. **PUT /shop/{shop-id}/review/{id}**
        1. Request Body : `title`, `content`, `password`
        2. shop-id를 가진 상점에 대한 `ShopReviewEntity` id 게시글 수정
        3. `writer`, `shop`은 변경 대상이 아니라고 가정합니다.
        4. 고려사항
            1. `password` 일치 여부 확인 작업 필요.
    5. **DELETE /shop/{shop-id}/review/{id}**
        1. Request Body : `password`
        2. shop-id를 가진 상점에 대한 `ShopReviewEntity` id 게시글 삭제
        3. 고려사항
            1. `password` 일치 여부 확인 작업 필요.

## 개선점

1. CRUD가 구현되어 있지 않습니다.
2. 어디까지나 구현 구상으로, 알 수 없는 동작 오류들이 많이 발생할 것으로 예상됩니다.
3. `ShopEntity` 의 수정, 삭제 과정에서 유효한 사용자의 요청인지 검사하는 부분이 고려되지 않고 있습니다. `owner`가 변경되지 않는다는 가정 하에, `owner`임을 확인하고 수정, 삭제 과정을 진행하는 방법을 생각해볼 수 있습니다.
