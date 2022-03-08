package dev.kalmh.challenge.entity.shop;

import dev.kalmh.challenge.entity.BoardEntity;
import dev.kalmh.challenge.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;


public class ShopPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;
    private String content;

    private String password;

    //어느 가게의 post인가
    @ManyToOne(
            targetEntity = ShopEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "shop_id")
    private ShopEntity shop;

    //작성자
    @OneToOne(
            targetEntity = UserEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private UserEntity writer;

    public ShopPostEntity() {}

    public ShopPostEntity(String title, String content, String password, ShopEntity shop, UserEntity writer) {
        this.title = title;
        this.content = content;
        this.password = password;
        this.shop = shop;
        this.writer = writer;
    }

    public void checkIsOwner(UserEntity writer) {
        if (this.shop.getOwner().getId() != writer.getId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }

    public UserEntity getWriter() {
        return writer;
    }

    public void setWriter(UserEntity writer) {
        this.writer = writer;
    }
}
