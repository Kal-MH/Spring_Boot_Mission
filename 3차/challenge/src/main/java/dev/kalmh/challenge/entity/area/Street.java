package dev.kalmh.challenge.entity.area;

import javax.persistence.Embeddable;

/*
 * 동, 면, 읍
 */
@Embeddable
public class Street {
    private String name;

    public Street() {}
    public Street(String name) {this.name = name;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Street{" +
                "name='" + name + '\'' +
                '}';
    }
}
