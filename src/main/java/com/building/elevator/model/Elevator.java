package com.building.elevator.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "elevator")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Elevator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long elevator_id;
    @Column(name = "current_floor")
    private int currentFloor;
    @Column(name = "current_direction")
    private Direction currentDirection;
    @Column(name = "current_state")
    private State currentState;
    @ManyToOne
    @JoinColumn(name = "elevator_building_id")
    private Building building;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "movement", joinColumns = {@JoinColumn(name = "elevator_id")}, inverseJoinColumns = {@JoinColumn(name = "movement_id")})
    private List<Movements> movements = new ArrayList<>();


}