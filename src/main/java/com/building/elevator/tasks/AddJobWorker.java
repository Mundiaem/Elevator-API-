package com.building.elevator.tasks;

import com.building.elevator.VO.Request;
import com.building.elevator.services.ElevatorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@ComponentScan
public class AddJobWorker {
    @Autowired
    private ElevatorServices elevator;
    private Request request;

    AddJobWorker(Request request) {
        this.request = request;
    }

    @Scheduled(fixedRate = 2000)
    @Async
    public void run() {
        elevator.addJob(request);
    }

}
