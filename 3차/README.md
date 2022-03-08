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
