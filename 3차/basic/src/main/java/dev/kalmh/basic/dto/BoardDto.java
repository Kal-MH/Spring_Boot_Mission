package dev.kalmh.basic.dto;

public class BoardDto {
    private int id;
    private String name;

    public BoardDto() {}
    public BoardDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
