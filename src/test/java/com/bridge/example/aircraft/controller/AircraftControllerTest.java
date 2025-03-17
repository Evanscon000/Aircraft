package com.bridge.example.aircraft.controller;

import com.bridge.example.aircraft.entity.Aircraft;
import com.bridge.example.aircraft.entity.Pilot;
import com.bridge.example.aircraft.service.AircraftService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AircraftController.class)
public class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AircraftService aircraftService;

    Pilot snoopy = new Pilot("Snoopy", ", the Beagle", 10);
    Pilot snoopyAce = new Pilot("Snoopy", ", the Ace", 10);
    Pilot baron = new Pilot("The Red", "Baron", 34);
    Pilot theBaron = new Pilot("The", "Baron", 34);

    Aircraft aircraft = new Aircraft("Dog House", snoopy);
    Aircraft updatedDogHouse = new Aircraft("Dog House Mk.II", snoopyAce);
    Aircraft updatedBiplane = new Aircraft("D.VII", theBaron);
    Aircraft deleteAircraft = new Aircraft("Pallet", new Pilot("Bob", "", 24));
    List<Aircraft> flight = new ArrayList<>();

    @BeforeEach
    void setUp() {
        flight.add(aircraft);
        aircraft.setId(1L);
        updatedDogHouse.setId(1L);
        updatedBiplane.setId(2L);
        deleteAircraft.setId(3L);

        Mockito.when(aircraftService.saveAircraft(Mockito.any(Aircraft.class))).thenReturn(aircraft);
        Mockito.when(aircraftService.findAllAircraft()).thenReturn(flight);
        Mockito.when(aircraftService.findAircraftById(anyLong())).thenReturn(aircraft);
        Mockito.when(aircraftService.updateAircraftById(anyLong(), any(Aircraft.class))).thenReturn(updatedDogHouse);
        Mockito.when(aircraftService.partialUpdateAircraft(anyLong(), any(Aircraft.class))).thenReturn(updatedBiplane);
        //Mockito.when(aircraftService.deleteAircraft(anyLong())).thenReturn(null);
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
                .andExpect(jsonPath("$.pilot.firstName").value("Snoopy"))
                .andExpect(jsonPath("$.pilot.lastName").value(", the Beagle"));
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

    @Test
    void shouldUpdateAircraftById() throws Exception {
        Aircraft updatedAircraft = new Aircraft();
        updatedAircraft.setId(1L);
        updatedAircraft.setAirframe("Dog House Mk.II");
        updatedAircraft.setPilot(snoopyAce);
        String updateContent = objectMapper.writeValueAsString(updatedAircraft);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/aircraft/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airframe").value("Dog House Mk.II"))
                .andExpect(jsonPath("$.pilot.firstName").value("Snoopy"))
                .andExpect(jsonPath("$.pilot.lastName").value(", the Ace"));
    }

    @Test
    void shouldUpdateAircraftAirframe() throws Exception {
        Aircraft existingAircraft = new Aircraft();
        existingAircraft.setId(2L);
        existingAircraft.setAirframe("D.VII");

        Aircraft updatedAircraft = new Aircraft();
        updatedAircraft.setPilot(theBaron);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/aircraft/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"airframe\":\"D.VII\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airframe").value("D.VII"));
    }

    @Test
    void shouldDeleteAircraft() throws Exception {
        doNothing().when(aircraftService).deleteAircraft(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/aircraft/3"))
                .andExpect(status().isNoContent());
    }

}