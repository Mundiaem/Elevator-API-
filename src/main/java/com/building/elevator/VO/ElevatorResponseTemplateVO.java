package com.building.elevator.VO;

import com.building.elevator.model.Elevator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElevatorResponseTemplateVO {
    List<Elevator> elevators;
    String response_status;
}
