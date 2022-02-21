package dev.kalmh.board.board;

import dev.kalmh.board.post.PostDto;
import dev.kalmh.board.post.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public boolean updateBoard(int id, BoardDto dto) {
        return this.boardRepository.update(id, dto);
    }

    @Override
    public boolean deleteBoard(int id) {
        return this.boardRepository.delete(id);
    }

    @Override
    public boolean createPost(int id, PostDto post) {
        BoardDto board = boardRepository.findById(id);
        int postId = postService.createPost(post);

        if (board == null)
            return false;
        board.getPostIds().add(postId);
        return true;
    }

    @Override
    public List<PostDto> readPostAll(int id) {
        List<PostDto> result = new ArrayList<>();
        BoardDto board = boardRepository.findById(id);

        for (int targetPostId: board.getPostIds()) {
            result.add(postService.readPost(targetPostId));
        }
        return result;
    }

    @Override
    public PostDto readPost(int id, int postId) {
        BoardDto board = boardRepository.findById(id);

        if (board == null)
            return null;
        int targetPostId = board.getPostIds().get(postId);
        return postService.readPost(targetPostId);
    }

    @Override
    public boolean updatePost(int id, int postId, PostDto post) {
        BoardDto board = boardRepository.findById(id);

        if (board == null)
            return false;
        int targetPostId = board.getPostIds().get(postId);
        return postService.updatePost(targetPostId, post);
    }

    @Override
    public boolean deletePost(int id, int postId, PostDto post) {
        BoardDto board = boardRepository.findById(id);

        if (board == null)
            return false;
        int targetPostId = board.getPostIds().get(postId);
        //만약, 데이터베이스 연결이 되어 있다면 postId에 대해서 Object를 얻어오고 비교가
        // 가능하지 않을까
        if (post.getPassword() != null) {
            board.getPostIds().remove(postId);
            return true;
        }
        return false;
    }
}
