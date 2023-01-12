package com.building.elevator.services;

import com.building.elevator.VO.BuildingResponseTemplateVO;
import com.building.elevator.model.Building;
import com.building.elevator.repository.BuildingRepository;
import com.building.elevator.repository.ElevatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {
    @Autowired
    public BuildingRepository buildingRepository;

    @Autowired
    public ElevatorRepository elevatorRepository;

    @Autowired
    public ElevatorServices elevatorServices;



  public Building save(Building building){
      return buildingRepository.save(building);

  }
  public int getCount(){
      return (int)buildingRepository.count();
  }
    public int getCountElevators(){
        return (int)elevatorRepository.count();
    }
    public BuildingResponseTemplateVO checkThenInsert(Building building) {
        //log.info(String.format(" getUserWithDepartment in  @ %s", TAG));
        BuildingResponseTemplateVO vo = new BuildingResponseTemplateVO();
        if(getCount()>0){
            vo.setBuilding(buildingRepository.findAll().get(0));
            vo.setResponse_status("number of floors and elevators already configured");
        }else{
            vo.setBuilding(save(building));
            vo.setResponse_status("Inserted the building floor and elevator configuration");

        }
        if(vo.getBuilding().getNoOfElevators() > getCountElevators()){
            int insert_count= vo.getBuilding().getNoOfElevators()- getCountElevators();
            elevatorServices.initializeElevators(insert_count);

        }
        return vo;
    }

}
