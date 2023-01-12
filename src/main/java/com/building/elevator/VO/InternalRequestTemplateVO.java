package com.building.elevator.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternalRequestTemplateVO {
    private int destinationFloor;
    @Override
    public String toString() {
        return "The destinationFloor is - " + destinationFloor;
    }

}
