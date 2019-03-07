package com.nike.parkinglot.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Parkinglot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Integer parkingSpaceSize;
    @OneToMany(mappedBy = "parkinglot")
    private List<ParkingSpace> parkingSpaces;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParkingSpaceSize() {
        return parkingSpaceSize;
    }

    public void setParkingSpaceSize(Integer parkingSpaceSize) {
        this.parkingSpaceSize = parkingSpaceSize;
    }

    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpace> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }
}
