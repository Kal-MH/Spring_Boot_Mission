package dev.kalmh.basic.dto;

public class UserDto {
    private int id;
    private String loginId;

    public UserDto(){}
    public UserDto(String loginId) {this.loginId = loginId;}
    public UserDto(int id, String loginId) {
        this.id = id;
        this.loginId = loginId;
    }

    public int getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }
}
