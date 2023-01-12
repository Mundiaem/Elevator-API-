package com.building.elevator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long building_id;
    @Column(name = "totalNoOfFloors")
    int totalNoOfFloors;
    @Column(name = "noOfElevators")
    int noOfElevators;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "elevators", joinColumns = {@JoinColumn(name = "building_id")}, inverseJoinColumns = {@JoinColumn(name = "elevator_id")})
    private List<Elevator> elevators = new ArrayList<Elevator>();



}
