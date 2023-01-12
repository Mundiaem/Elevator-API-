package com.building.elevator.model;

import javax.persistence.*;

@Entity
@Table(name="elevator_movements")
public class Elevator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "currentFloor")
    private int currentFloor = 0;
    @Column(name = "currentDirection")
    private Direction currentDirection = Direction.UP;
    @Column(name = "currentState")
    private State currentState = State.IDLE;

}