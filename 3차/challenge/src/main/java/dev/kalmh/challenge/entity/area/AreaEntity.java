package dev.kalmh.challenge.entity.area;

import dev.kalmh.challenge.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "area")
public class AreaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "name", column=@Column(name = "province"))
    private Province province;

    @Embedded
    @AttributeOverride(name = "name", column=@Column(name = "city"))
    private City city;

    @Embedded
    @AttributeOverride(name = "name", column=@Column(name = "street"))
    private Street street;

    @Embedded
    @Column(name = "location")
    private LocationData location;

    public AreaEntity() {}

    public AreaEntity(Province province, City city, Street street, LocationData location) {
        this.province = province;
        this.city = city;
        this.street = street;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public LocationData getLocation() {
        return location;
    }

    public void setLocation(LocationData location) {
        this.location = location;
    }
}
