package dev.kalmh.board.board;

import java.util.List;

public interface BoardRepository {
    boolean save(BoardDto dto); // 새로운 게시판 생성
    List<BoardDto> findByAll(); // 게시판 목록 불러오기
    BoardDto findById(int id); // 특정 게시판 불러오기
    boolean update(int id, BoardDto dto); // 기존 게시판 이름 변경
    boolean delete(int id);
}
