package com.building.elevator.controller;

import com.building.elevator.model.Elevator;
import com.building.elevator.repository.ElevatorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Tag(description = "Elevator API", name = "Elevator Services")
@Controller
public class ElevatorController {

    private final AtomicInteger counter = new AtomicInteger();

    private final ElevatorRepository elevatorRepository;

    @Autowired
    public ElevatorController(ElevatorRepository userRepository) {
        this.elevatorRepository = userRepository;
    }

    @GetMapping(value = "/elevators", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @Operation(summary = "Returns all elevators", tags = {"Elevator",},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Returns all elevators",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Elevator.class)))
            })
    @ResponseBody
    public Flux<Object> streamJsonObjects() {
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
