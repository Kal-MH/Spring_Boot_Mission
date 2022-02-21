package dev.kalmh.board.board;

import dev.kalmh.board.post.PostDto;

import java.util.List;

public interface BoardService {
    //게시판 관련
    void createBoard(BoardDto dto);
    List<BoardDto> readBoardAll();
    BoardDto readBoard(int id);
    boolean updateBoard(int id, BoardDto dto);
    boolean deleteBoard(int id);

    //게시글 관련
    boolean createPost(int id, PostDto post);
    List<PostDto> readPostAll(int id);
    PostDto readPost(int id, int postId);
    boolean updatePost(int id, int postId, PostDto post);
    boolean deletePost(int id, int postId, PostDto post);
}
