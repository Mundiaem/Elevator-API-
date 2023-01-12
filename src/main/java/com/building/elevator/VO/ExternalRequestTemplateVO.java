package com.building.elevator.VO;

import com.building.elevator.model.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalRequestTemplateVO {
    private Direction directionToGo;
    private int sourceFloor;
    @Override
    public String toString() {
        return " The Elevator has been requested on floor - " + sourceFloor + " and the person wants go in the - "
                + directionToGo;
    }
}
