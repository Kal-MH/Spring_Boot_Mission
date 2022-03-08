package dev.kalmh.challenge.entity;

import dev.kalmh.challenge.entity.area.AreaEntity;
import dev.kalmh.challenge.entity.shop.ShopEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String loginId;

    //내가 쓴 글에 대한 리스트
    @OneToMany(
            targetEntity = PostEntity.class,
            fetch = FetchType.LAZY,
            mappedBy = "writer"
    )
    private List<PostEntity> postEntityList = new ArrayList<>();

    //사용자의 거주지
    @OneToOne(
            targetEntity = AreaEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "area_id")
    private AreaEntity area;

    //내가 소유하고 있는 상점
    @OneToMany(
            targetEntity = ShopEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "shop_id")
    private List<ShopEntity> shopEntityList = new ArrayList<>();

    //상점주인 Onset
    private boolean isOwner;

    public UserEntity() {}
    public UserEntity(String loginId) {
        this.loginId = loginId;
    }

    public UserEntity(String loginId, AreaEntity area) {
        this.loginId = loginId;
        this.area = area;
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public List<PostEntity> getPostEntityList() {
        return postEntityList;
    }

    public void setPostEntityList(List<PostEntity> postEntityList) {
        this.postEntityList = postEntityList;
    }

    public AreaEntity getArea() {
        return area;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    public List<ShopEntity> getShopEntityList() {
        return shopEntityList;
    }

    public void setShopEntityList(List<ShopEntity> shopEntityList) {
        this.shopEntityList = shopEntityList;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }
}
