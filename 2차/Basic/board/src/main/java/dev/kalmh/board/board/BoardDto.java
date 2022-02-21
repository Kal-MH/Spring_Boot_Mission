package dev.kalmh.board.board;

import java.util.ArrayList;
import java.util.List;

public class BoardDto {
    private int id;
    private String categoryName;
    //각각의 게시판마다 게시글 리스트를 가지고 있다.
    private List<Integer> postIds = new ArrayList<>();

    public BoardDto() {}
    public BoardDto(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Integer> getPostIds() {
        return postIds;
    }
}
