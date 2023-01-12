package com.building.elevator.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "elevator_movements")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Elevator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long elevator_id;
    @Column(name = "currentFloor")
    private int currentFloor;
    @Column(name = "currentDirection")
    private Direction currentDirection;
    @Column(name = "currentState")
    private State currentState;



}