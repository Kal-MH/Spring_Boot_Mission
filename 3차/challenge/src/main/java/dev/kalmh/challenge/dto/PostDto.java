package dev.kalmh.challenge.dto;

public class PostDto {
    private int id;
    private String title;
    private String content;
    private String password;
    private int userId;
    private int boardId;

    public PostDto(int id, String title, String content, String password, int userId, int boardId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.password = password;
        this.userId = userId;
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

    public int getBoardId() {
        return boardId;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId() {
        return userId;
    }
}
