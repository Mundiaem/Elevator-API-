package com.building.elevator.services;

import com.building.elevator.VO.Request;
import com.building.elevator.VO.vo;
import com.building.elevator.model.Direction;
import com.building.elevator.model.Elevator;
import com.building.elevator.model.State;
import com.building.elevator.repository.ElevatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Service
public class ElevatorServices {
    @Autowired
    private ElevatorRepository elevatorRepository;

    public List<Elevator> saveElevators(List<Elevator> elevators) {
        return elevatorRepository.saveAll(elevators);
    }

    public List<Elevator> initializeElevators(int elevatorCount) {
        List<Elevator> elevators = new ArrayList<>();
        while (elevatorCount > 0) {
            Elevator elevator = new Elevator();
            elevator.setCurrentDirection(Direction.UP);
            elevator.setCurrentFloor(1);
            elevator.setCurrentState(State.IDLE);
            elevators.add(elevator);
            elevatorCount--;
        }
        return saveElevators(elevators);
    }
    public List<Elevator> findAll() {
        return elevatorRepository.findAll();

    }
    /**
     * jobs which are being processed
     */
    private TreeSet<Request> currentJobs = new TreeSet<>();
    /**
     * up jobs which cannot be processed now so put in pending queue
     */
    private TreeSet<Request> upPendingJobs = new TreeSet<>();
    /**
     * down jobs which cannot be processed now so put in pending queue
     */
    private TreeSet<Request> downPendingJobs = new TreeSet<>();

    public void startElevator() {
        System.out.println("The Elevator has started functioning");
        Elevator elevator= new Elevator();
        while (true) {

            if (checkIfJob()) {

                if (elevator.getCurrentDirection() == Direction.UP) {
                    Request request = currentJobs.pollFirst();
                    processUpRequest(request);
                    if (currentJobs.isEmpty()) {
                        addPendingDownJobsToCurrentJobs();

                    }

                }
                if (elevator.getCurrentDirection() == Direction.DOWN) {
                    Request request = currentJobs.pollLast();
                    processDownRequest(request);
                    if (currentJobs.isEmpty()) {
                        addPendingUpJobsToCurrentJobs();
                    }

                }
            }
        }
    }
    public boolean checkIfJob() {

        if (currentJobs.isEmpty()) {
            return false;
        }
        return true;

    }
    private void processUpRequest(Request request) {
        Elevator elevator= new Elevator();

        int startFloor = elevator.getCurrentFloor();
        // The elevator is not on the floor where the person has requested it i.e. source floor. So first bring it there.
        if (startFloor < request.getExternalRequest().getSourceFloor()) {
            for (int i = startFloor; i <= request.getExternalRequest().getSourceFloor(); i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("We have reached floor -- " + i);
                elevator.setCurrentFloor(i);
            }
        }
        // The elevator is now on the floor where the person has requested it i.e. source floor. User can enter and go to the destination floor.
        System.out.println("Reached Source Floor--opening door");

        startFloor = elevator.getCurrentFloor();

        for (int i = startFloor + 1; i <= request.getInternalRequest().getDestinationFloor(); i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("We have reached floor -- " + i);
            elevator.setCurrentFloor(i);
            if (checkIfNewJobCanBeProcessed(request)) {
                break;
            }
        }

    }
    private void processDownRequest(Request request) {
        Elevator elevator= new Elevator();

        int startFloor =elevator.getCurrentFloor();
        if (startFloor < request.getExternalRequest().getSourceFloor()) {
            for (int i = startFloor; i <= request.getExternalRequest().getSourceFloor(); i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("We have reached floor -- " + i);
                elevator.setCurrentFloor(i);
            }
        }

        System.out.println("Reached Source Floor--opening door");

        startFloor = elevator.getCurrentFloor();

        for (int i = startFloor - 1; i >= request.getInternalRequest().getDestinationFloor(); i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("We have reached floor -- " + i);
            elevator.setCurrentFloor(i);
            if (checkIfNewJobCanBeProcessed(request)) {
                break;
            }
        }

    }
    private boolean checkIfNewJobCanBeProcessed(Request currentRequest) {
        Elevator elevator= new Elevator();

        if (checkIfJob()) {
            if (elevator.getCurrentDirection() == Direction.UP) {
                Request request = currentJobs.pollLast();
                if (request.getInternalRequest().getDestinationFloor() < currentRequest.getInternalRequest()
                        .getDestinationFloor()) {
                    currentJobs.add(request);
                    currentJobs.add(currentRequest);
                    return true;
                }
                currentJobs.add(request);

            }

            if (elevator.getCurrentDirection() == Direction.DOWN) {
                Request request = currentJobs.pollFirst();
                if (request.getInternalRequest().getDestinationFloor() > currentRequest.getInternalRequest()
                        .getDestinationFloor()) {
                    currentJobs.add(request);
                    currentJobs.add(currentRequest);
                    return true;
                }
                currentJobs.add(request);

            }

        }
        return false;

    }
    private void addPendingDownJobsToCurrentJobs() {
        Elevator elevator= new Elevator();

        if (!downPendingJobs.isEmpty()) {
            System.out.println("Pick a pending down job and execute it by putting in current job");
            currentJobs = downPendingJobs;
            elevator.setCurrentDirection(Direction.DOWN);
        } else {
            elevator.setCurrentState(State.IDLE);
            System.out.println("The elevator is in Idle state");
        }

    }

    private void addPendingUpJobsToCurrentJobs() {
        Elevator elevator= new Elevator();

        if (!upPendingJobs.isEmpty()) {
            System.out.println("Pick a pending up job and execute it by putting in current job");

            currentJobs = upPendingJobs;
            elevator.setCurrentDirection(Direction.UP);
        } else {
            elevator.setCurrentState(State.IDLE);
            System.out.println("The elevator is in Idle state");

        }

    }
}
