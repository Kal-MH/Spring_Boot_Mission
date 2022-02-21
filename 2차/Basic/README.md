# Basic Docs

## Data Form

- **PostDto**
    - `id` : Primary Key. (List상에서의 Index값)
    - `title` : 제목
    - `content` : 글
    - `writer` : 작성자
    - `password` : 비밀번호
- **BoardDto**
    - `id` : Primary Key. (AutoIncrement 된다고 가정)
    - `categoryName` : 게시판 제목
    - `postIds` : 각각의 게시판마다 게시글의 id를 저장하고 있는 List

## API 설계

현재 board 서버에서는 2개의 List를 가지고 있습니다.

- boardList
- postList

모든 게시글(PostDto)은 postList에 저장되며, boardList에 저장된 게시판(BoardDto)들은 각각 등록된 게시글들의 id값 즉, postList 상에서의
인덱스값들을 가지고 있습니다.

```
예시)

postList -> {0, 1, 2, 3, 4}
boardList -> {
	{0, 2, 4},
	{1, 3}
}
```

### Create

- **POST /board**
    - 게시판 생성
- **POST /board/{id}/post**
    - 게시판 게시글 생성
    - `id` : 게시판 id

### Read

- **GET /board**
    - 전체 게시판 목록 조회
- **GET /board/{id}**
    - 특정 게시판 조회
    - `id` : 게시판 id
- **GET /board/{id}/post**
    - 특정 게시판 전체 게시글 조회
    - `id` : 게시판 id
- **GET /board/{id}/post/{post-order}**
    - 특정 게시판 특정 게시글 조회
    - `id` : 게시판 id
    - `post-order` : List 상에서의 게시글 index.

### Update

- **PUT /board/{id}**
    - 특정 게시판 이름 수정
    - `id` : 게시판 id
- **PUT /board/{id}/post/{post-order}**
    - 특정 게시판 특정 게시글 수정
    - `id` : 게시판 id
    - `post-order` : List 상에서의 게시글 index.

### Delete

- **DELETE /board/{id}**
    - 특정 게시판 삭제
    - `id` : 게시판 id
- **DELETE /board/{id}/post/{post-order}**
    - 특정 게시판 특정 게시글 삭제
    - `id` : 게시판 id
    - `post-order` : List 상에서의 게시글 index.

## 예시화면

### Read

- **GET /board**

![print_board_all](https://user-images.githubusercontent.com/59648372/154988688-88957a6d-ad7f-4149-bcee-151b4a209a00.png)

- **GET /board/0**

![board_getbyid](https://user-images.githubusercontent.com/59648372/154989170-2cfd6d77-7354-48d2-a57b-2e9e0fd6194a.png)

- **GET /board/0/post**

![print_one_board_posts](https://user-images.githubusercontent.com/59648372/154988851-d9ed959b-3b2b-41c3-b47a-fa12cd9185b7.png)

- **GET /board/0/post/0**

![print_one_board_post](https://user-images.githubusercontent.com/59648372/154989231-4a5d1e54-d43c-4a34-aeb1-d9ba6f4902db.png)

### Update

- **PUT /board/1**

![update_board](https://user-images.githubusercontent.com/59648372/154989375-464601a5-9eb2-4f03-8c99-4d780968da2f.png)

- **PUT /board/0/post/0**

![update_post](https://user-images.githubusercontent.com/59648372/154989388-dd7884ef-9e11-4c31-a58e-cf5093c435aa.png)

### Delete

- **DELETE /board/1**

![delete_board](https://user-images.githubusercontent.com/59648372/154989545-ccbb191f-c977-4f38-809f-64317e5b6f0e.png)

- **DELETE /board/0/post/0**

![delete_post](https://user-images.githubusercontent.com/59648372/154989558-ba75396c-50c0-4b0b-abb9-03532fba7975.png)

## 개선점

1. 게시글의 id는 postList의 인덱스값. 
    1. DB를 사용하게 된다면 각각의 board마다 테이블 작성하고 각각의 테이블이 가지는 primary key를 이용해서 접근하는 방법을 고려해봅니다.
2. 실제 게시글을 삭제하지 않고, board의 postIds 리스트에서만 제거.
    1. mysql DB를 사용한다는 가정 하에,  foreign key로 연결된 tuple은 같이 삭제해주는 `on delete cascade` 사용을 고려해 봅니다.
