package dev.kalmh.basic;

import dev.kalmh.basic.dto.PostDto;
import dev.kalmh.basic.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public PostController(
            @Autowired PostService postService
    ) {
        this.postService = postService;
    }
    //POST
    @PostMapping("/board/{board-id}/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@PathVariable("board-id") int boardId, @RequestBody PostDto dto) {this.postService.createPost(boardId, dto);}

    //GET ALL
    @GetMapping("/post")
    public List<PostDto> readPostAll() {
        return this.postService.readPostAll();
    }
    //GET ALL WITH BOARDID
    @GetMapping("/board/{board-id}/post")
    public List<PostDto> readPostAll(@PathVariable("board-id") int boardId) {
        return this.postService.readPostAll(boardId);
    }

    //GET
    @GetMapping("/post/{id}")
    public PostDto readPost(@PathVariable("id") int id) {
        return this.postService.readPost(id);
    }
    //GET WITH BOARDID
    @GetMapping("/board/{board-id}/post/{post-idx}")
    public PostDto readPost(@PathVariable("board-id") int boardId, @PathVariable("post-idx") int idx) {
        return this.postService.readPost(boardId, idx);
    }

    //UPDATE
    @PutMapping("/post/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePost(
            @PathVariable("id") int id,
            @RequestBody PostDto postDto
    ) {
        this.postService.updatePost(id, postDto);
    }
    //UPDATE WITH BOARDID
    @PutMapping("/board/{board-id}/post/{post-idx}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePost(
            @PathVariable("board-id") int boardId,
            @PathVariable("post-idx") int idx,
            @RequestBody PostDto postDto
    ) {
        this.postService.updatePost(boardId, idx, postDto);
    }

    //DELETE
    @DeleteMapping("/post/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePost(
            @PathVariable("id") int id,
            @RequestBody PostDto dto
    ) {
        this.postService.deletePost(id, dto);
    }
    @DeleteMapping("/board/{board-id}/post/{post-idx}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePost(
            @PathVariable("board-id") int boardId,
            @PathVariable("post-idx") int idx,
            @RequestBody PostDto dto
    ) {
        this.postService.deletePost(boardId, idx, dto);
    }
}
