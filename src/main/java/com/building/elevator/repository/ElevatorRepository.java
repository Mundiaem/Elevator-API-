package com.building.elevator.repository;

import com.building.elevator.model.Elevator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ElevatorRepository extends JpaRepository<Elevator, Long> {







}
