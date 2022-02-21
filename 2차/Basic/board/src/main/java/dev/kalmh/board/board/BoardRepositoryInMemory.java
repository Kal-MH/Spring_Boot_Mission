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
        int id;
        if (this.boardList.size() == 0)
            id = 0;
        else {
            BoardDto lastDto = this.boardList.get(this.boardList.size() - 1);
            id = lastDto.getId() + 1;
        }
        dto.setId(id);
        return this.boardList.add(dto);
    }

    @Override
    public List<BoardDto> findByAll() {
        return this.boardList;
    }

    @Override
    public BoardDto findById(int id) {
        for (BoardDto b: this.boardList) {
            if (b.getId() == id)
                return b;
        }
        return null;
    }

    @Override
    public boolean update(int id, BoardDto dto) {
        BoardDto target;
        for(int i = 0; i < this.boardList.size(); i++) {
            target = this.boardList.get(i);
            if (target.getId() == id && target.getCategoryName() != null) {
                target.setCategoryName(dto.getCategoryName());
                this.boardList.set(i, target);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        BoardDto target;
        for(int i = 0; i < this.boardList.size(); i++) {
            target = this.boardList.get(i);
            if (target.getId() == id) {
                this.boardList.remove(i);
                return true;
            }
        }
        return false;
    }
}
