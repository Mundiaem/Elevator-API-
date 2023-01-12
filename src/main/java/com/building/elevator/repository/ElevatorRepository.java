package com.building.elevator.repository;

import com.building.elevator.model.Elevator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ElevatorRepository {

    List<Elevator> users = new ArrayList<>();

    public List<Elevator> findAll(){
        return users;
    }


    public boolean deleteUser(int id){
        return users.removeIf(user -> user.getId() == id);
    }


}
