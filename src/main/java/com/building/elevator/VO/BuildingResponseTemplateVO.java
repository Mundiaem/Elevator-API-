package com.building.elevator.VO;

import com.building.elevator.model.Building;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingResponseTemplateVO {
    Building building;
    String response_status;
}
