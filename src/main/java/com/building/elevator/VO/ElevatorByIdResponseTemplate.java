package com.building.elevator.VO;

import com.building.elevator.model.Direction;
import com.building.elevator.model.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElevatorByIdResponseTemplate {
    Long elevator_id;
    private int currentFloor;
    private Direction currentDirection;
    private State currentState;
}
