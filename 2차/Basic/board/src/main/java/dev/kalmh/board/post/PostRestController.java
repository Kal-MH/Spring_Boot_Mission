package dev.kalmh.board.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostRestController {
    private static final Logger logger = LoggerFactory.getLogger(PostRestController.class);
    private final PostService postService;

    public PostRestController(
            @Autowired PostService postService
    ) {
        this.postService = postService;
    }

    // POST /post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody PostDto postDto) {
        logger.info(postDto.toString());
        this.postService.createPost(postDto);
    }

    // GET /post
    @GetMapping()
    public List<PostDto> readPostAll() {
        logger.info("in read post all");
        return this.postService.readPostAll();
    }

    //GET /post/0/
    @GetMapping("{id}")
    public PostDto readPost(@PathVariable("id") int id) {
        logger.info("read one");
        return this.postService.readPost(id);
    }

    // PUT /post/0
    @PutMapping("{id}")
    public ResponseEntity updatePost(
            @PathVariable("id") int id,
            @RequestBody PostDto postDto
    ) {
        logger.info("update");
        if (this.postService.updatePost(id, postDto))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    // DELETE /post/0
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePost(@PathVariable("id") int id) {
        this.postService.deletePost(id);
    }
}
