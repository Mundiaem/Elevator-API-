package com.building.elevator.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallElevatorTemplateVO {
  private  int destinationFloor;
    private  int sourceFloor;

}
