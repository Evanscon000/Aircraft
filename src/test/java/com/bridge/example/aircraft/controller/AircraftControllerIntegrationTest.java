package com.bridge.example.aircraft.controller;

import com.bridge.example.aircraft.entity.Aircraft;
import com.bridge.example.aircraft.entity.Pilot;
import com.bridge.example.aircraft.repository.AircraftRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AircraftControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AircraftRepository aircraftRepository;

    Pilot snoopy;
    Pilot baron;
    Aircraft doghouse;
    Aircraft triplane;

    @BeforeEach
    void setUp() {
        snoopy = new Pilot( "Snoopy", ", the Beagle", 10);
        baron = new Pilot("The Red", "Baron", 34);
        doghouse = new Aircraft("Doghouse", snoopy);
        triplane = new Aircraft("Dr.1", baron);

    }

    @Test
    public void shouldCreateAircraft() throws Exception {
        String doghouseJson = objectMapper.writeValueAsString(doghouse);

        MvcResult savedAircraft = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/aircraft")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(doghouseJson))
                .andReturn();
        String expectType = savedAircraft.getRequest().getContentType();
        Aircraft expectedAircraft = objectMapper.readValue(savedAircraft.getResponse().getContentAsString(), Aircraft.class);

        assertEquals(expectType, "application/json");
        assertEquals(expectedAircraft.getAirframe(), doghouse.getAirframe());
        assertEquals(expectedAircraft.getPilot().getFirstName(), doghouse.getPilot().getFirstName());
    }

    @Test
    public void shouldGetAllAircraft() throws Exception {
        aircraftRepository.save(doghouse);
        aircraftRepository.save(triplane);

        mockMvc.perform(get("/api/aircraft"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                //.andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].airframe").value("Doghouse"))
                .andExpect(jsonPath("$[1].airframe").value("Dr.1"))
                //.andExpect(jsonPath("$[0].pilot.id").value(1L))
                .andExpect(jsonPath("$[0].pilot.firstName").value("Snoopy"))
                .andExpect(jsonPath("$[1].pilot.firstName").value("The Red"))
                //.andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].pilot.lastName").value("Baron"))
                .andExpect(jsonPath("$[1].pilot.age").value(34));

    }

    @Test
    public void shouldGetAircraftById() throws Exception {
        Aircraft savedAircraft = aircraftRepository.save(doghouse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/aircraft/" + savedAircraft.getId()))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.airframe").value("Doghouse"))
                //.andExpect(jsonPath("$.pilot.id").value(1L))
                .andExpect(jsonPath("$.pilot.firstName").value("Snoopy"))
                .andExpect(jsonPath("$.pilot.lastName").value(", the Beagle"))
                .andExpect(jsonPath("$.pilot.age").value(10));
    }

}
