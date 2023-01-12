package com.building.elevator.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Comparable<vo.Request> {
    private InternalRequestTemplateVO internalRequest;
    private ExternalRequestTemplateVO externalRequest;
    @Override
    public int compareTo(vo.Request req) {
        if (this.getInternalRequest().getDestinationFloor() == req.getInternalRequest().getDestinationFloor())
            return 0;
        else if (this.getInternalRequest().getDestinationFloor() > req.getInternalRequest().getDestinationFloor())
            return 1;
        else
            return -1;
    }
}
