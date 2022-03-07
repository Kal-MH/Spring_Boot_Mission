package dev.kalmh.basic.dao;

import dev.kalmh.basic.dto.BoardDto;
import dev.kalmh.basic.entity.BoardEntity;
import dev.kalmh.basic.repository.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.Optional;

@Repository
public class BoardDao {
    private static final Logger logger = LoggerFactory.getLogger(BoardDao.class);
    private final BoardRepository boardRepository;

    public BoardDao(
            @Autowired BoardRepository boardRepository
    ) {
        this.boardRepository = boardRepository;
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
        this.boardRepository.delete(targetEntity.get());
    }
}
