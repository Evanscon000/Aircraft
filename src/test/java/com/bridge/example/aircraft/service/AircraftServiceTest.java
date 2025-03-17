package com.bridge.example.aircraft.service;

import com.bridge.example.aircraft.entity.Aircraft;
import com.bridge.example.aircraft.entity.Pilot;
import com.bridge.example.aircraft.repository.AircraftRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AircraftServiceTest {

    @Mock
    AircraftRepository aircraftRepository;

    @InjectMocks
    AircraftService aircraftService;

    Pilot snoopy = new Pilot("Snoopy", "the Beagle", 10);
    Pilot baron = new Pilot("The Red ", "Baron", 34);
    private Aircraft doghouse;
    private Aircraft triplane;
    List<Aircraft> flight;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doghouse = new Aircraft("Dog House", snoopy);
        triplane = new Aircraft("DR-1", baron);
        flight = new ArrayList<>(List.of(doghouse, triplane));
    }

    @Test
    void shouldSaveNewAircraft() {
        when(aircraftRepository.save(doghouse)).thenReturn(doghouse);
        Aircraft actualRequest = aircraftService.saveAircraft(doghouse);
        verify (aircraftRepository, times (1)).save(any(Aircraft.class));
        assertThat(actualRequest).isEqualTo(doghouse);
    }

    @Test
    void shouldFindAllAircraft() {
        when(aircraftRepository.findAll()).thenReturn(flight);
        List<Aircraft> listOfAircraftRequest = aircraftService.findAllAircraft();
        verify(aircraftRepository, times(1)).findAll();
        assertThat(listOfAircraftRequest).isEqualTo(flight);
    }

    @Test
    void shouldFindAircraftById() {
        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(doghouse));
        Aircraft actualRequest = aircraftService.findAircraftById(1L);
        verify (aircraftRepository, times (1)).findById(1L);
        assertThat(actualRequest).isEqualTo(doghouse);
    }

}
