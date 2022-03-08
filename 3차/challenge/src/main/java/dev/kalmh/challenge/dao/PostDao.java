package dev.kalmh.challenge.dao;

import dev.kalmh.challenge.dto.PostDto;
import dev.kalmh.challenge.entity.BoardEntity;
import dev.kalmh.challenge.entity.PostEntity;
import dev.kalmh.challenge.entity.UserEntity;
import dev.kalmh.challenge.repository.BoardRepository;
import dev.kalmh.challenge.repository.PostRepository;
import dev.kalmh.challenge.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class PostDao {
    private static final Logger logger = LoggerFactory.getLogger(PostDao.class);
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    public PostDao(
            @Autowired PostRepository postRepository,
            @Autowired UserRepository userRepository,
            @Autowired BoardRepository boardRepository
            ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    //POST
    public void createPost(int boardId, PostDto dto) {
        Optional<UserEntity> user = userRepository.findById((long)dto.getUserId());
        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Optional<BoardEntity> board = this.boardRepository.findById((long)boardId);
        if (board.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        PostEntity postEntity = new PostEntity(
                dto.getTitle(),
                dto.getContent(),
                dto.getPassword(),
                user.get(),
                board.get()
        );
        PostEntity savedPost = this.postRepository.save(postEntity);
        board.get().savePost(savedPost);
    }

    //GET ALL
    public Iterator<PostEntity> readPostAll() {return this.postRepository.findAll().iterator();}
    public List<PostEntity> readPostAll(int boardId) {
        Optional<BoardEntity> board = this.boardRepository.findById((long)boardId);
        if (board.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return board.get().getPostEntityList();
    }

    //GET
    public PostEntity readPost(int id) {
        Optional<PostEntity> postEntity = this.postRepository.findById((long)id);
        if (postEntity.isEmpty()) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
        return postEntity.get();
    }
    public PostEntity readPost(int boardId, int idx) {
        Optional<BoardEntity> board = this.boardRepository.findById((long)boardId);
        if (board.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return board.get().getPostEntityList().get(idx);
    }

    //UPDATE
    public void updatePost(int id, PostDto dto) {
        Optional<PostEntity> targetEntity = this.postRepository.findById(Long.valueOf(id));
        if (targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        PostEntity postEntity = targetEntity.get();

        if (!postEntity.getPassword().equals(dto.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        postEntity.setTitle(dto.getTitle() == null ? postEntity.getTitle() : dto.getTitle());
        postEntity.setContent(dto.getContent() == null ? postEntity.getContent() : dto.getContent());
        this.postRepository.save(postEntity);
    }
    public void updatePost(int boardId, int idx, PostDto dto) {
        Optional<BoardEntity> board = this.boardRepository.findById((long)boardId);
        if (board.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        if (idx >= board.get().getPostEntityList().size())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        PostEntity postEntity = board.get().getPostEntityList().get(idx);
        if (!postEntity.getPassword().equals(dto.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        postEntity.setTitle(dto.getTitle() == null ? postEntity.getTitle() : dto.getTitle());
        postEntity.setContent(dto.getContent() == null ? postEntity.getContent() : dto.getContent());
        this.postRepository.save(postEntity);
    }

    //DELETE
    public void deletePost(int id, PostDto dto) {
        Optional<PostEntity> targetEntity = this.postRepository.findById(Long.valueOf(id));
        if (targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        PostEntity postEntity = targetEntity.get();

        if (!postEntity.getPassword().equals(dto.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        this.postRepository.delete(postEntity);
    }
    public void deletePost(int boardId, int idx, PostDto dto) {
        Optional<BoardEntity> board = this.boardRepository.findById((long)boardId);
        if (board.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        List<PostEntity> postEntityList = board.get().getPostEntityList();
        if (idx >= postEntityList.size())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        PostEntity postEntity = postEntityList.get(idx);
        if (!postEntity.getPassword().equals(dto.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        this.postRepository.delete(postEntity);
    }
}
