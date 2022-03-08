package dev.kalmh.challenge.service;

import dev.kalmh.challenge.dao.BoardDao;
import dev.kalmh.challenge.dto.BoardDto;
import dev.kalmh.challenge.entity.BoardEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BoardService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final BoardDao boardDao;

    public BoardService(
            @Autowired BoardDao boardDao
    ) {
        this.boardDao = boardDao;
    }

    //POST
    public void createBoard(BoardDto dto) {this.boardDao.createBoard(dto);}

    //GET ALL
    public List<BoardDto> readBoardAll() {
        Iterator<BoardEntity> iterator = this.boardDao.readBoardAll();
        List<BoardDto> boardList = new ArrayList<>();
        while (iterator.hasNext()) {
            BoardEntity boardEntity = iterator.next();
            boardList.add(new BoardDto(
                    Math.toIntExact(boardEntity.getId()),
                    boardEntity.getName()
            ));
        }
        return boardList;
    }

    //GET
    public BoardDto readBoard(int id) {
        BoardEntity boardEntity = this.boardDao.readBoard(id);
        BoardDto board = new BoardDto(
                Math.toIntExact(boardEntity.getId()),
                boardEntity.getName()
        );
        return board;
    }

    //UPDATE
    public void updateBoard(int id, BoardDto dto) {
        this.boardDao.updatePost(id, dto);
    }

    //DELETE
    public void deleteBoard(int id) {
        this.boardDao.deletePost(id);
    }
}
