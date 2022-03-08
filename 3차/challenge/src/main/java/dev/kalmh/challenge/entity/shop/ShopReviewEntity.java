package dev.kalmh.challenge.entity.shop;

import dev.kalmh.challenge.entity.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "shop_review")
public class ShopReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;
    private String content;

    private String password;

    //어느 가게에 대한 리뷰인가
    @ManyToOne(
            targetEntity = ShopEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "shop_id")
    private ShopEntity shop;

    //작성자
    @ManyToOne(
            targetEntity = UserEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private UserEntity writer;

    public ShopReviewEntity() {}

    public ShopReviewEntity(String title, String content, String password, ShopEntity shop, UserEntity writer) {
        this.title = title;
        this.content = content;
        this.password = password;
        this.shop = shop;
        this.writer = writer;
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
