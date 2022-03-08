package dev.kalmh.challenge.entity.area;

import javax.persistence.Embeddable;

@Embeddable
public class LocationData {
    private Long latitude; //위도 ex) 127.10522081658463
    private Long longitude; //경도 ex) 37.35951219616309

    public LocationData() {}
    public LocationData(Long latitude, Long longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "LocationData{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
