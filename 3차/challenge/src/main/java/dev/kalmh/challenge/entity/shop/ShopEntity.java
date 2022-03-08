package dev.kalmh.challenge.entity.shop;

import dev.kalmh.challenge.entity.UserEntity;
import dev.kalmh.challenge.entity.area.AreaEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shop")
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //취급 품목
    @OneToMany(
            targetEntity = CategoryEntity.class,
            fetch = FetchType.LAZY
    )
    private List<CategoryEntity> categoryEntityList = new ArrayList<>();

    //가게 위치
    @OneToOne(
            targetEntity = AreaEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "area_id")
    private AreaEntity area;

    //가게 주인
   @OneToOne(
            targetEntity = UserEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private UserEntity owner;

    //가게 post
    @OneToMany(
            targetEntity = CategoryEntity.class,
            fetch = FetchType.LAZY
    )
    private List<ShopPostEntity> shopPostEntityList = new ArrayList<>();

    //가게 review
    @OneToMany(
            targetEntity = CategoryEntity.class,
            fetch = FetchType.LAZY
    )
    private List<ShopReviewEntity> shopReviewEntityList = new ArrayList<>();

    public ShopEntity() {}
    public ShopEntity(String name, AreaEntity area, UserEntity owner) {
        this.name = name;
        this.area = area;
        this.owner = owner;
        checkOwnerOnset();
    }

    public void checkOwnerOnset() {
        if (!this.owner.isOwner())
            this.owner.setOwner(true);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryEntity> getCategoryEntityList() {
        return categoryEntityList;
    }

    public void setCategoryEntityList(List<CategoryEntity> categoryEntityList) {
        this.categoryEntityList = categoryEntityList;
    }

    public AreaEntity getArea() {
        return area;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public List<ShopPostEntity> getShopPostEntityList() {
        return shopPostEntityList;
    }

    public void setShopPostEntityList(List<ShopPostEntity> shopPostEntityList) {
        this.shopPostEntityList = shopPostEntityList;
    }

    public List<ShopReviewEntity> getShopReviewEntityList() {
        return shopReviewEntityList;
    }

    public void setShopReviewEntityList(List<ShopReviewEntity> shopReviewEntityList) {
        this.shopReviewEntityList = shopReviewEntityList;
    }
}
