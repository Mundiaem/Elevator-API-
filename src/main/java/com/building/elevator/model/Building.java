package com.building.elevator.model;

import jakarta.persistence.*;


@Entity
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "totalNoOfFloors")
    int totalNoOfFloors;

    public Building(int totalNoOfFloors) {
        this.totalNoOfFloors = totalNoOfFloors;
    }

    public Building() {

    }

    public int getTotalNoOfFloors() {
        return totalNoOfFloors;
    }

    public void setTotalNoOfFloors(int totalNoOfFloors) {
        this.totalNoOfFloors = totalNoOfFloors;
    }
}
