package com.building.elevator.repository;

import com.building.elevator.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
}
