package com.building.elevator.tasks;

import com.building.elevator.services.ElevatorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class ProcessJobWorker implements Runnable {
    @Autowired
    private ElevatorServices elevator;


    @Override
    public void run() {
        /**
         * start the elevator
         */
        elevator.startElevator();
    }

}
