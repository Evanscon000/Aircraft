package com.bridge.example.aircraft.service;

import com.bridge.example.aircraft.entity.Aircraft;
import com.bridge.example.aircraft.entity.Pilot;
import com.bridge.example.aircraft.repository.PilotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PilotServiceTest {

    @Mock
    PilotRepository pilotRepository;

    @InjectMocks
    PilotService pilotService;

    Pilot snoopy = new Pilot(1L,"Snoopy", "the Beagle", 10);
    Pilot baron = new Pilot(2L, "The Red", "Baron", 32);
    List<Pilot> pilots;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        snoopy.setId(1L);
        baron.setId(2L);
        pilots = new ArrayList<>(List.of(snoopy, baron));
    }

    @Test
    void shouldCreateNewPilot() {
        when(pilotRepository.save(snoopy)).thenReturn(snoopy);
        Pilot actualRequest = pilotService.savePilot(snoopy);
        verify (pilotRepository, times (1)).save(any(Pilot.class));
        assertThat(actualRequest).isEqualTo(snoopy);
    }

    @Test
    void shouldFindAllPilots() {
        when(pilotRepository.findAll()).thenReturn(pilots);
        List<Pilot> actualRequest = pilotService.findAllPilots();
        verify(pilotRepository, times(1)).findAll();
        assertThat(actualRequest).isEqualTo(pilots);
    }

    @Test
    void shouldFindSinglePilot() {
        when(pilotRepository.findById(2L)).thenReturn(Optional.of(baron));
        Pilot actualRequest = pilotService.findPilotById(2L);
        verify(pilotRepository, times(1)).findById(2L);
        assertThat(actualRequest).isEqualTo(baron);
    }

}