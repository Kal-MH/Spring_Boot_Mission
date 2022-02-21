package dev.kalmh.board.board;

import dev.kalmh.board.post.PostDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<BoardDto> readBoardAll() {
        logger.info("read all board");
        return this.boardService.readBoardAll();
    }

    @GetMapping("{id}")
    public BoardDto readBoard(@PathVariable("id") int id) {
        return this.boardService.readBoard(id);
    }

    @PutMapping("{id}")
    public ResponseEntity updateBoard(
            @PathVariable("id") int id,
            @RequestBody BoardDto dto
    ) {
        if (this.boardService.updateBoard(id, dto))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteBoard(
            @PathVariable("id") int id
    ) {
        if (this.boardService.deleteBoard(id))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}/post")
    public ResponseEntity createBoardPost(
            @PathVariable("id") int id,
            @RequestBody PostDto post
    ) {
        if (this.boardService.createPost(id, post))
            return new ResponseEntity(HttpStatus.CREATED);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}/post")
    public List<PostDto> readBoardPostAll(
            @PathVariable("id") int id
    ) {
        return this.boardService.readPostAll(id);
    }

    @GetMapping("/{id}/post/{post-order}")
    public PostDto readBoardPost(
            @PathVariable("id") int id,
            @PathVariable("post-order") int postOrder
    ) {
        return this.boardService.readPost(id, postOrder);
    }

    @PutMapping("/{id}/post/{post-order}")
    public ResponseEntity updateBoardPost(
            @PathVariable("id") int id,
            @PathVariable("post-order") int postOrder,
            @RequestBody PostDto post
    ) {
        logger.info("board post update");
        if (this.boardService.updatePost(id, postOrder, post))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}/post/{post-order}")
    public ResponseEntity deleteBoardPost(
            @PathVariable("id") int id,
            @PathVariable("post-order") int postOrder,
            @RequestBody PostDto post
    ) {
        logger.info("board post delete");
        if (this.boardService.deletePost(id, postOrder, post))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
