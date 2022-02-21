package dev.kalmh.board.board;

import dev.kalmh.board.post.PostDto;

import java.util.List;

public interface BoardService {
    //게시판 관련
    void createBoard(BoardDto dto);
    List<BoardDto> readBoardAll();
    BoardDto readBoard(int id);
    void updateBoard(int id, BoardDto dto);
    void deleteBoard(int id);

    //게시글 관련
    void createPost(int id, PostDto post);
    void readPost(int id);
    PostDto readPostOne(int id, int postId);
    void updatePost(int id, int postId, PostDto post);
    void deletePost(int id, int postId, PostDto post);
}
