package com.bridge.example.aircraft.controller;

import com.bridge.example.aircraft.entity.Pilot;
import com.bridge.example.aircraft.service.PilotService;
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
import static org.mockito.ArgumentMatchers.refEq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PilotController.class)
public class PilotControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private PilotService pilotService;

    Pilot snoopy;
    Pilot baron;
    List<Pilot> pilots;

    @BeforeEach
    void setUp() {
        snoopy = new Pilot(1L, "Snoopy", "The Beagle", 10);
        baron = new Pilot(2L, "The Red", "Baron", 34);
        pilots = new ArrayList<>(List.of(snoopy, baron));

        Mockito.when(pilotService.savePilot(Mockito.any(Pilot.class))).thenReturn(snoopy);
        Mockito.when(pilotService.findAllPilots()).thenReturn(pilots);
        Mockito.when(pilotService.findPilotById(Mockito.anyLong())).thenReturn(baron);
    }

    @Test
    void shouldCreateNewPilot() throws Exception {
        String snoopyJson = objectMapper.writeValueAsString(snoopy);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/pilot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(snoopyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Snoopy"));
        Mockito.verify(pilotService).savePilot(refEq(snoopy));
    }

    @Test
    void shouldFIndAllPilots() throws Exception {
        mockMvc.perform(get("/api/pilot"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("The Red"))
                .andExpect(jsonPath("$[0].lastName").value("The Beagle"))
                .andExpect(jsonPath("$[1].age").value("34"));
        Mockito.verify(pilotService).findAllPilots();
    }

    @Test
    void shouldFindPilotById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pilot/2"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").value("The Red"))
                .andExpect(jsonPath("$.lastName").value("Baron"))
                .andExpect(jsonPath("$.age").value(34));
        Mockito.verify(pilotService).findPilotById(2L);
    }
}
