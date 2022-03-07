package dev.kalmh.basic.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String loginId;

    @Column(nullable = false, length = 30)
    private String password;

    /*
    TODO
    - 사용자 조회 시, 지금까지 작성한 게시글 보여주기
     */

    public UserEntity() {}
    public UserEntity(String loginId) {
        this.loginId = loginId;
    }
    public UserEntity(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }
}
