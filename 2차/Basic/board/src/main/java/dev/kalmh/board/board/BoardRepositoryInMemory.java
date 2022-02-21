package dev.kalmh.board.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardRepositoryInMemory implements BoardRepository{
    private static final Logger logger = LoggerFactory.getLogger(BoardRepositoryInMemory.class);
    private List<BoardDto> boardList = new ArrayList<>(); //전체 게시판 목록

    public BoardRepositoryInMemory() {}


    @Override
    public boolean save(BoardDto dto) {
        //primary key 수동 설정
        dto.setId(this.boardList.size());
        return this.boardList.add(dto);
    }

    @Override
    public List<BoardDto> findByAll() {
        return this.boardList;
    }

    @Override
    public BoardDto findById(int id) {
        return this.boardList.get(id);
    }

    //기존 게시판 이름 갱신하기
    @Override
    public boolean update(int id, BoardDto dto) {
        BoardDto targetDto = this.boardList.get(id);

        if (targetDto.getCategoryName() != null) {
            targetDto.setCategoryName(dto.getCategoryName());
        }
        this.boardList.set(id, targetDto);
        return true;
    }

    @Override
    public boolean delete(int id) {
        this.boardList.remove(id);
        return true;
    }
}
