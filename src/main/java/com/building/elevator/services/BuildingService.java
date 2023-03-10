package com.building.elevator.services;

import com.building.elevator.VO.BuildingResponseTemplateVO;
import com.building.elevator.VO.ConfigureTemplateVO;
import com.building.elevator.VO.DeleteTemplate;
import com.building.elevator.model.Building;
import com.building.elevator.model.Elevator;
import com.building.elevator.repository.BuildingRepository;
import com.building.elevator.repository.ElevatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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

    public BuildingResponseTemplateVO getBuilding() {
        List<Building> buildingList = new ArrayList<>(buildingRepository.findAll());
        LOGGER.debug(String.format("Getting the building config %s", buildingList.size()));

        return new BuildingResponseTemplateVO(buildingList.get(0), "Building response");
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
            Building res = save(building);

            List<Elevator> elevatorList = new ArrayList<>();
            for (int i = 0; i < floor.getNo_of_elevators(); i++) {
               elevatorServices.initializeElevators(res);

            }

            vo.setBuilding(res);
            vo.setResponse_status("Inserted the building floor and elevator configuration");

        }

        return vo;
    }

    public DeleteTemplate deleteConfig() {
        buildingRepository.deleteAll();
        return new DeleteTemplate("Successfully deleted building config", true);
    }

}
