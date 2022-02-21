package dev.kalmh.board.board;

import dev.kalmh.board.post.PostDto;
import dev.kalmh.board.post.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceSimple implements BoardService{
    private static final Logger logger = LoggerFactory.getLogger(BoardServiceSimple.class);
    private final BoardRepository boardRepository;
    private final PostService postService;

    public BoardServiceSimple(
            @Autowired BoardRepository boardRepository,
            @Autowired PostService postService
    ) {
        this.boardRepository = boardRepository;
        this.postService = postService;
    }

    @Override
    public void createBoard(BoardDto dto) {
        if (!this.boardRepository.save(dto))
            throw new RuntimeException("board save failed");
    }

    @Override
    public List<BoardDto> readBoardAll() {
        return this.boardRepository.findByAll();
    }

    @Override
    public BoardDto readBoard(int id) {
        return this.boardRepository.findById(id);
    }

    @Override
    public void updateBoard(int id, BoardDto dto) {
        this.boardRepository.update(id, dto);
    }

    @Override
    public void deleteBoard(int id) {
        this.boardRepository.delete(id);
    }

    @Override
    public void createPost(int id, PostDto post) {
        //속해 있는 게시판 가져오기
        BoardDto board = boardRepository.findById(id);
        int postId = postService.createPost(post);

        if (board != null)
            board.getPostIds().add(postId);
    }

    @Override
    public void readPost(int id) {

    }

    @Override
    public PostDto readPostOne(int id, int postId) {
        BoardDto board = boardRepository.findById(id);
        if (board != null) {
            int targetPostId = board.getPostIds().get(postId);
            return postService.readPost(targetPostId);
        }
        return null;
    }

    @Override
    public void updatePost(int id, int postId, PostDto post) {
        BoardDto board = boardRepository.findById(id);
        if (board != null) {
            int targetPostId = board.getPostIds().get(postId);
            postService.updatePost(targetPostId, post);
        }
    }

    @Override
    public void deletePost(int id, int postId, PostDto post) {
        BoardDto board = boardRepository.findById(id);

        if (board != null) {
            int targetPostId = board.getPostIds().get(postId);
            if (post.getPassword() != null)
                postService.deletePost(targetPostId);
        }
    }
}
