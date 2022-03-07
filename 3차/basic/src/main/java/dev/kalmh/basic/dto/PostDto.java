package dev.kalmh.basic.dto;

public class PostDto {
    private int id;
    private String title;
    private String content;
    private String writer;
    private int boardId;

    public PostDto(int id, String title, String content, String writer, int boardId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.boardId = boardId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public int getBoardId() {
        return boardId;
    }
}
