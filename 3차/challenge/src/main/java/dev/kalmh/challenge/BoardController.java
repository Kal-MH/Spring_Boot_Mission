package dev.kalmh.challenge;

import dev.kalmh.challenge.dto.BoardDto;
import dev.kalmh.challenge.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("board")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    private final BoardService boardService;

    public BoardController(
            @Autowired BoardService boardService
    ) {
        this.boardService = boardService;
    }

    //POST
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody BoardDto dto){
        logger.info("createPost called");
        this.boardService.createBoard(dto);
    }

    //GET ALL
    @GetMapping()
    public List<BoardDto> readBoardAll() {
        return this.boardService.readBoardAll();
    }

    //GET
    @GetMapping("{id}")
    public BoardDto readBoard(@PathVariable("id") int id) {
        return this.boardService.readBoard(id);
    }

    //UPDATE
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBoard(
            @PathVariable("id") int id,
            @RequestBody BoardDto dto
    ) {
        this.boardService.updateBoard(id, dto);
    }

    //DELETE
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBoard( @PathVariable("id") int id) {
        this.boardService.deleteBoard(id);
    }
}
