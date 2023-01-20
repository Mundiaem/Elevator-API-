package com.building.elevator.services;

import com.building.elevator.VO.*;
import com.building.elevator.model.Building;
import com.building.elevator.model.Direction;
import com.building.elevator.model.Elevator;
import com.building.elevator.model.State;
import com.building.elevator.repository.ElevatorRepository;
import com.building.elevator.tasks.AddJobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.TreeSet;

@Service
public class ElevatorServices {
    @Autowired
    private ElevatorRepository elevatorRepository;
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

    public Elevator saveElevator(Elevator elevators) {
        return elevatorRepository.save(elevators);
    }

    public Elevator initializeElevators(Building building) {
        Elevator elevator = new Elevator();
        elevator.setCurrentDirection(Direction.UP);
        elevator.setCurrentFloor(0);
        elevator.setBuilding(building);
        elevator.setCurrentState(State.IDLE);
        return saveElevator(elevator);
    }

    public ElevatorResponseTemplateVO findAll() {

        return new ElevatorResponseTemplateVO(elevatorRepository.findAll(), "All Elevators");

    }

    public ElevatorByIdResponseTemplate findElevatorByID(RequestById request) {
        Optional<Elevator> elevator = elevatorRepository.findById(request.getId());
        return new ElevatorByIdResponseTemplate(elevator.get().getElevator_id(), elevator.get().getCurrentFloor()
                , elevator.get().getCurrentDirection(), elevator.get().getCurrentState());

    }

    public void startElevator() {
        System.out.println("The Elevator has started functioning");
        Elevator elevator = new Elevator();
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
        Elevator elevator = new Elevator();

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
        Elevator elevator = new Elevator();

        int startFloor = elevator.getCurrentFloor();
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
        Elevator elevator = new Elevator();

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
        Elevator elevator = new Elevator();

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
        Elevator elevator = new Elevator();

        if (!upPendingJobs.isEmpty()) {
            System.out.println("Pick a pending up job and execute it by putting in current job");

            currentJobs = upPendingJobs;
            elevator.setCurrentDirection(Direction.UP);
        } else {
            elevator.setCurrentState(State.IDLE);
            System.out.println("The elevator is in Idle state");

        }

    }

    public void addJob(Request request) {
        Elevator elevator = new Elevator();

        if (elevator.getCurrentState() == State.IDLE) {
            elevator.setCurrentState(State.MOVING);
            elevator.setCurrentDirection(request.getExternalRequest().getDirectionToGo());
            currentJobs.add(request);
        } else if (elevator.getCurrentState() == State.MOVING) {

            if (request.getExternalRequest().getDirectionToGo() != elevator.getCurrentDirection()) {
                addtoPendingJobs(request);
            } else if (request.getExternalRequest().getDirectionToGo() == elevator.getCurrentDirection()) {
                if (elevator.getCurrentDirection() == Direction.UP
                        && request.getInternalRequest().getDestinationFloor() < elevator.getCurrentFloor()
                ) {
                    addtoPendingJobs(request);
                } else if (elevator.getCurrentDirection() == Direction.DOWN
                        && request.getInternalRequest().getDestinationFloor() > elevator.getCurrentFloor()) {
                    addtoPendingJobs(request);
                } else {
                    currentJobs.add(request);
                }

            }

        }

    }

    public void addtoPendingJobs(Request request) {
        if (request.getExternalRequest().getDirectionToGo() == Direction.UP) {
            System.out.println("Add to pending up jobs");
            upPendingJobs.add(request);
        } else {
            System.out.println("Add to pending down jobs");
            downPendingJobs.add(request);
        }
    }

    public void moveElevator(CallElevatorTemplateVO call) {
        ExternalRequestTemplateVO er = new ExternalRequestTemplateVO(Direction.UP, call.getSourceFloor());
        InternalRequestTemplateVO ir = new InternalRequestTemplateVO(call.getDestinationFloor());
        Request request1 = new Request(ir, er);
        new AddJobWorker(request1);
    }
}
