package com.bridge.example.aircraft.controller;

import com.bridge.example.aircraft.entity.Pilot;
import com.bridge.example.aircraft.repository.PilotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PilotControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PilotRepository pilotRepository;

    Pilot snoopy = new Pilot( "Snoopy", ", the Beagle", 10);
    Pilot baron = new Pilot("The Red", "Baron", 32);

    @Test
    void shouldCreatePilot() throws Exception {
        String pilotJson = objectMapper.writeValueAsString(snoopy);
        mockMvc.perform(post("/api/pilot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pilotJson))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.firstName").value("Snoopy"))
                .andExpect(jsonPath("$.lastName").value(", the Beagle"))
                .andExpect(jsonPath("$.age").value(10));
    }

    @Test
    void shouldGetAllPilots() throws Exception {
        pilotRepository.save(snoopy);
        pilotRepository.save(baron);

        mockMvc.perform(get("/api/pilot"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].firstName").value("Snoopy"));
    }

    @Test
    void shouldGetPilotById() throws Exception {
        Pilot savedPilot = pilotRepository.save(snoopy);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/pilot/" + savedPilot.getId()))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Snoopy"));

    }

}
