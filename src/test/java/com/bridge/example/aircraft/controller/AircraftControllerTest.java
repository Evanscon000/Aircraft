package com.bridge.example.aircraft.controller;

import com.bridge.example.aircraft.entity.Aircraft;
import com.bridge.example.aircraft.service.AircraftService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AircraftService aircraftService;

    Aircraft aircraft = new Aircraft("Dog House", "Snoopy, the Beagle");
    List<Aircraft> flight = new ArrayList<>();

    @BeforeEach
    void setUp() {
        flight.add(aircraft);
        aircraft.setId(1L);

        Mockito.when(aircraftService.saveAircraft(Mockito.any(Aircraft.class))).thenReturn(aircraft);
        Mockito.when(aircraftService.findAllAircraft()).thenReturn(flight);
        Mockito.when(aircraftService.findAircraftById(Mockito.anyLong())).thenReturn(aircraft);
    }

    @Test
    void shouldCreateAircraft() throws Exception {
        String doghouseJson = objectMapper.writeValueAsString(aircraft);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/aircraft")
                .contentType(MediaType.APPLICATION_JSON)
                .content(doghouseJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.airframe").value("Dog House"))
                .andExpect(jsonPath("$.pilot").value("Snoopy, the Beagle"));
        Mockito.verify(aircraftService).saveAircraft(any(Aircraft.class));
    }

    @Test
    void shouldGetAircraft() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aircraft"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        Mockito.verify(aircraftService).findAllAircraft();
    }

    @Test
    void shouldGetAircraftById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aircraft/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        Mockito.verify(aircraftService).findAircraftById(1L);
    }

}
