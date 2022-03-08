package dev.kalmh.basic.dao;

import dev.kalmh.basic.dto.BoardDto;
import dev.kalmh.basic.entity.BoardEntity;
import dev.kalmh.basic.entity.PostEntity;
import dev.kalmh.basic.repository.BoardRepository;
import dev.kalmh.basic.repository.PostRepository;
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
public class BoardDao {
    private static final Logger logger = LoggerFactory.getLogger(BoardDao.class);
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    public BoardDao(
            @Autowired BoardRepository boardRepository,
            @Autowired PostRepository postRepository
    ) {
        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
    }

    //POST
    public void createBoard(BoardDto dto) {
        BoardEntity boardEntiry = new BoardEntity();
        boardEntiry.setName(dto.getName());
        this.boardRepository.save(boardEntiry);
    }

    //GET ALL
    public Iterator<BoardEntity> readBoardAll() {
        return this.boardRepository.findAll().iterator();
    }

    //GET
    public BoardEntity readBoard(int id) {
        Optional<BoardEntity> boardEntity = this.boardRepository.findById((long)id);
        if (boardEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return boardEntity.get();
    }

    //UPDATE
    public void updatePost(int id, BoardDto dto) {
        Optional<BoardEntity> targetEntity = this.boardRepository.findById((long)id);
        if (targetEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        BoardEntity boardEntity = targetEntity.get();
        boardEntity.setName(dto.getName());
        this.boardRepository.save(boardEntity);
    }

    //DELETE
    public void deletePost(int id) {
        Optional<BoardEntity> targetEntity = this.boardRepository.findById((long)id);
        if (targetEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        List<PostEntity> postEntityList = targetEntity.get().getPostEntityList();
        for(PostEntity post : postEntityList) {
            this.postRepository.delete(post);
        }
        this.boardRepository.delete(targetEntity.get());
    }
}
