package com.building.elevator.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElevatorRequestTemplateVO {
    int currentFloor;
    int toFloor;
}
