package com.building.elevator.services;

import com.building.elevator.VO.BuildingResponseTemplateVO;
import com.building.elevator.VO.ConfigureTemplateVO;
import com.building.elevator.model.Building;
import com.building.elevator.model.Direction;
import com.building.elevator.model.Elevator;
import com.building.elevator.model.State;
import com.building.elevator.repository.BuildingRepository;
import com.building.elevator.repository.ElevatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingService {
    @Autowired
    public BuildingRepository buildingRepository;

    @Autowired
    public ElevatorRepository elevatorRepository;

    @Autowired
    public ElevatorServices elevatorServices;


    public Building save(Building building) {
        return buildingRepository.save(building);

    }

    public int getCount() {
        return (int) buildingRepository.count();
    }

    public int getCountElevators() {
        return (int) elevatorRepository.count();
    }

    public List<Building> getBuilding() {
        return buildingRepository.findAll();
    }

    public BuildingResponseTemplateVO checkThenInsert(ConfigureTemplateVO floor) {
        //log.info(String.format(" getUserWithDepartment in  @ %s", TAG));
        BuildingResponseTemplateVO vo = new BuildingResponseTemplateVO();
        if (getCount() > 0) {
            vo.setBuilding(buildingRepository.findAll().get(0));
            vo.setResponse_status("number of floors and elevators already configured");
        } else {

            Building building = new Building();
            building.setTotalNoOfFloors(floor.getNo_of_floors());
            building.setNoOfElevators(floor.getNo_of_elevators());
            List<Elevator> elevatorList = new ArrayList<>();
            for (int i = 0; i < floor.getNo_of_elevators(); i++) {
                Elevator elevator = new Elevator();
                elevator.setCurrentState(State.IDLE);
                elevator.setCurrentFloor(0);
                elevator.setCurrentDirection(Direction.UP);
                elevatorList.add(elevator);

            }
            building.setElevators(elevatorList);
            Building res = save(building);

            vo.setBuilding(res);
            vo.setResponse_status("Inserted the building floor and elevator configuration");

        }
        if (vo.getBuilding().getNoOfElevators() > getCountElevators()) {
            elevatorServices.initializeElevators();

        }
        return vo;
    }

}
