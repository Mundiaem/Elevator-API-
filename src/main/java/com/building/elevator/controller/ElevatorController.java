package com.building.elevator.controller;

import com.building.elevator.VO.BuildingResponseTemplateVO;
import com.building.elevator.VO.ElevatorResponseTemplateVO;
import com.building.elevator.model.Building;
import com.building.elevator.model.Elevator;
import com.building.elevator.repository.ElevatorRepository;
import com.building.elevator.services.BuildingService;
import com.building.elevator.services.ElevatorServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Tag(description = "Elevator API", name = "Elevator")
@RestController
@RequestMapping(value = "/v1/api")
public class ElevatorController {

    private final AtomicInteger counter = new AtomicInteger();
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
   public BuildingResponseTemplateVO configureNumberOfFloors(@RequestBody Building floors){
      return buildingService.checkThenInsert(floors );

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
    public List<Elevator> getAllElevators() {
        return elevatorServices.findAll();
    }

    @GetMapping(value = "/elevator_call", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Call The elevator", tags = {"Elevator",},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Call The elevator",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ElevatorResponseTemplateVO.class)))
            })
    @ResponseBody
    public Flux<Object> callTheElevator() {


        return Flux.interval(Duration.ofSeconds(1)).map(i -> "Elevator");
    }

    @GetMapping(value = "/elevator", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @Operation(summary = "Register a new elevator",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Returns the registered elevator",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Elevator.class)))
            })
    @ResponseBody
    public Flux<Object> streamDataFlux() {
        return Flux.interval(Duration.ofSeconds(1)).map(i -> "Data stream line - " + i );
    }


}
