package dev.kalmh.challenge.entity.area;

import javax.persistence.Embeddable;

/*
 * 도, 광역시
 */
@Embeddable
public class Province {
    private String name;

    public Province() {}

    public Province(String name) {
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
        return "Province{" +
                "name='" + name + '\'' +
                '}';
    }
}
