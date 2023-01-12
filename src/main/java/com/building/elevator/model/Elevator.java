package com.building.elevator.model;


import jakarta.persistence.*;

@Entity
@Table(name = "elevator_movements")
public class Elevator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "currentFloor")
    private int currentFloor;
    @Column(name = "currentDirection")
    private Direction currentDirection;
    @Column(name = "currentState")
    private State currentState;
    @Column(name = "totalNoOfFloors")
    int totalNoOfFloors;

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public int getTotalNoOfFloors() {
        return totalNoOfFloors;
    }

    public void setTotalNoOfFloors(int totalNoOfFloors) {
        this.totalNoOfFloors = totalNoOfFloors;
    }
}