package com.building.elevator.services;

import com.building.elevator.model.Building;
import com.building.elevator.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {
    @Autowired
    public BuildingRepository buildingRepository;


  public Building save(int noOfFloors){
      return buildingRepository.save(new Building(noOfFloors));

  }
}
