package dev.kalmh.auth.entity.model;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("User")
public class UserInfo implements Serializable {
    private String id;
    private String username;
    private int status;

    public UserInfo() {}

    public UserInfo(String id, int status, String username) {
        this.id = id;
        this.status = status;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", status=" + status +
                '}';
    }
}
