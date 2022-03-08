package dev.kalmh.challenge.entity.area;

import javax.persistence.Embeddable;

/*
 * 시, 군, 구
 */
@Embeddable
public class City {
    private String name;

    public City() {}

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                '}';
    }
}
