package com.building.elevator.services;

import com.building.elevator.model.Direction;
import com.building.elevator.model.Elevator;
import com.building.elevator.model.State;
import com.building.elevator.repository.ElevatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElevatorServices {
    @Autowired
    private ElevatorRepository elevatorRepository;

    public List<Elevator> saveElevators(List<Elevator> elevators) {
        return elevatorRepository.saveAll(elevators);
    }

    public List<Elevator> initializeElevators(int elevatorCount) {
        List<Elevator> elevators = new ArrayList<>();
        while (elevatorCount > 0) {
            Elevator elevator = new Elevator();
            elevator.setCurrentDirection(Direction.UP);
            elevator.setCurrentFloor(1);
            elevator.setCurrentState(State.IDLE);
            elevators.add(elevator);
            elevatorCount--;
        }
        return saveElevators(elevators);
    }
    public List<Elevator> findAll() {
        return elevatorRepository.findAll();

    }
}
