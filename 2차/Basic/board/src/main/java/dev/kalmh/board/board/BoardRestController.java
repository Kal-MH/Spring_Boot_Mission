package dev.kalmh.board.board;

import dev.kalmh.board.post.PostDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("board")
public class BoardRestController {
    private static final Logger logger = LoggerFactory.getLogger(BoardRestController.class);
    private final BoardService boardService;

    public BoardRestController(@Autowired BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createBoard(@RequestBody BoardDto dto) {
        logger.info("create board");
        this.boardService.createBoard(dto);
    }

    @GetMapping()
    public List<BoardDto> readBoard() {
        logger.info("read all board");
        return this.boardService.readBoardAll();
    }

    @GetMapping("{id}")
    public BoardDto readBoard(@PathVariable("id") int id) {
        return this.boardService.readBoard(id);
    }

    @PutMapping("{id}")
    public void updateBoard(
            @PathVariable("id") int id,
            @RequestBody BoardDto dto
    ) {
        this.boardService.updateBoard(id, dto);
    }

    @PostMapping("/{id}/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBoardPost(
            @PathVariable("id") int id,
            @RequestBody PostDto post
    ) {
        this.boardService.createPost(id, post);
    }

    @GetMapping("/{id}/post/{postId}")
    public PostDto readBoardPost(
            @PathVariable("id") int id,
            @PathVariable("postId") int postId
    ) {
        return this.boardService.readPostOne(id, postId);
    }

    @PutMapping("/{id}/post/{postId}")
    public void updateBoardPost(
            @PathVariable("id") int id,
            @PathVariable("postId") int postId,
            @RequestBody PostDto post
    ) {
        this.boardService.updatePost(id, postId, post);
    }

    @DeleteMapping("/{id}/post/{postId}")
    public void deleteBoardPost(
            @PathVariable("id") int id,
            @PathVariable("postId") int postId,
            @RequestBody PostDto post
    ) {
        this.boardService.deletePost(id, postId, post);
    }
}
