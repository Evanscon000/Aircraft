package com.bridge.example.aircraft.controller;

import com.bridge.example.aircraft.entity.Aircraft;
import com.bridge.example.aircraft.service.AircraftService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class AircraftController {

    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @PostMapping("/aircraft")
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        return aircraftService.saveAircraft(aircraft);

    }

    @GetMapping("/aircraft")
    public ArrayList<Aircraft> getAircraft() {
        return aircraftService.findAllAircraft();
    }

    @GetMapping("/aircraft/{aircraftId}")
    public Aircraft getAircraftById(@PathVariable Long aircraftId) {
        return aircraftService.findAircraftById(aircraftId);
    }
}
