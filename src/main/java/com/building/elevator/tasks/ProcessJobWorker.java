package com.building.elevator.tasks;

import com.building.elevator.VO.vo;

public class ProcessJobWorker implements Runnable {

    private Elevator elevator;

    ProcessJobWorker(vo.Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void run() {
        /**
         * start the elevator
         */
        elevator.startElevator();
    }

}
