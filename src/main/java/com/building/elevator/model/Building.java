package com.building.elevator.model;

import jakarta.persistence.*;


@Entity
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "totalNoOfFloors")
    int totalNoOfFloors;
    @Column(name = "noOfElevators")
    int noOfElevators;

    public Building(int totalNoOfFloors, int noOfElevators) {
        this.totalNoOfFloors = totalNoOfFloors;
        this.noOfElevators= noOfElevators;
    }

    public Building() {

    }

    public int getNoOfElevators() {
        return noOfElevators;
    }

    public void setNoOfElevators(int noOfElevators) {
        this.noOfElevators = noOfElevators;
    }

    public int getTotalNoOfFloors() {
        return totalNoOfFloors;
    }

    public void setTotalNoOfFloors(int totalNoOfFloors) {
        this.totalNoOfFloors = totalNoOfFloors;
    }

}
