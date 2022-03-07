# 

# Basic

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
- 
