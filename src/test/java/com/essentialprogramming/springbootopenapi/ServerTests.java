package com.essentialprogramming.springbootopenapi;

import com.building.elevator.Server;
import com.building.elevator.model.Direction;
import com.building.elevator.model.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = Server.class)
class ServerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testBuildingConfig() throws Exception {
        mockMvc.perform(get("/v1/api/building_config")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.building.noOfElevators").value("4"))
                .andExpect(jsonPath("$.building.totalNoOfFloors").value("20"));

    }

    @Test
    public void testElevatorById() throws Exception {
        mockMvc.perform(get("/v1/api/get_elevator_by_id?id=5")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.elevator_id").value("5"))
                .andExpect(jsonPath("$.currentFloor").value("0"))
                .andExpect(jsonPath("$.currentDirection").value("UP"))
                .andExpect(jsonPath("$.currentState").value("IDLE"));

    }
}
