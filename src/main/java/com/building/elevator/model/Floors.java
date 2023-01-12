package com.building.elevator.model;

import javax.persistence.*;

@Entity
@Table(name="building_floors")
public class Floors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "totalNoOfFloors")
    int totalNoOfFloors;
}
