package com.building.elevator.controller;

import com.building.elevator.VO.*;
import com.building.elevator.model.Direction;
import com.building.elevator.model.Elevator;
import com.building.elevator.model.State;
import com.building.elevator.services.BuildingService;
import com.building.elevator.services.ElevatorServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Tag(description = "Elevator API", name = "Elevator")
@RestController
@RequestMapping(value = "/v1/api")
public class ElevatorController {

    @Autowired
    private ElevatorServices elevatorServices;

    @Autowired
    private BuildingService buildingService;

    @PostMapping("/configure_floor")
    @Operation(summary = "configure number of floors ", tags = {"Elevator",},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Returns all number of floors",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BuildingResponseTemplateVO.class)))
            })
    public BuildingResponseTemplateVO configureNumberOfFloors(@RequestBody ConfigureTemplateVO floors) {
        return buildingService.checkThenInsert(floors);

    }

    @GetMapping("/building_config")
    @Operation(summary = "Get building ", tags = {"Elevator",},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Get building configuration",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BuildingResponseTemplateVO.class)))
            })
    public BuildingResponseTemplateVO getBuildingConfiguration() {
        return buildingService.getBuilding();

    }

    @DeleteMapping("/building_config")
    @Operation(summary = "Delete building config", tags = {"Elevator",},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Delete building configuration",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DeleteTemplate.class)))
            })
    public ResponseEntity<DeleteTemplate> deleteBuildingConfiguration() {
        return new ResponseEntity<>(buildingService.deleteConfig(), HttpStatus.OK);


    }

    @GetMapping(value = "/elevators", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns all elevators", tags = {"Elevator",},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Returns all elevators",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ElevatorResponseTemplateVO.class)))
            })
    @ResponseBody
    public ResponseEntity<ElevatorResponseTemplateVO> getAllElevators() {
        return new ResponseEntity<>(elevatorServices.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/get_elevator_by_id", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get elevator by Id", tags = {"Elevator",},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Get elevator by ID",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ElevatorByIdResponseTemplate.class)))
            })
    @ResponseBody
    public ResponseEntity<ElevatorByIdResponseTemplate> getElevatorById(RequestById requestById) {
        return new ResponseEntity<>(elevatorServices.findElevatorByID(requestById), HttpStatus.OK);
    }

    @GetMapping(value = "/elevator_call", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @Operation(summary = "Call The elevator", tags = {"Elevator",}
    )
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> callTheElevator(CallElevatorTemplateVO call) {
        elevatorServices.moveElevator(call);
        StreamingResponseBody responseBody = response -> {
            for (int i = 1; i <= 10; i++) {
                Elevator elevator= new Elevator();
                elevator.setCurrentDirection(Direction.UP);
                elevator.setCurrentState(State.MOVING);

                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(elevator) +"\n";
                response.write(jsonString.getBytes());
                response.flush();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(responseBody);    }


}
