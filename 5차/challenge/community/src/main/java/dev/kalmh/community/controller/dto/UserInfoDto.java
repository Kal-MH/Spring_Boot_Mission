package dev.kalmh.community.controller.dto;

public class UserInfoDto {
    private String id;
    private String username;
    private int status;

    public UserInfoDto() {}

    public UserInfoDto(String id, String username, int status) {
        this.id = id;
        this.username = username;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "UserInfoDto{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", id=" + id +
                '}';
    }
}