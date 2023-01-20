package com.building.elevator.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigureTemplateVO {
    private int no_of_floors;
    private int no_of_elevators;
}
