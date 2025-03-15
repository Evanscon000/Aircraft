package com.bridge.example.aircraft.controller;

import com.bridge.example.aircraft.entity.Aircraft;
import com.bridge.example.aircraft.service.AircraftService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

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
    public List<Aircraft> getAllAircraft() {
        return aircraftService.findAllAircraft();
    }

    @GetMapping("/aircraft/{aircraftId}")
    public Aircraft getAircraftById(@PathVariable Long aircraftId) {
        return aircraftService.findAircraftById(aircraftId);
    }

    @PutMapping("/aircraft/{id}")
    public Aircraft updateAircraftById(@PathVariable Long id, @RequestBody Aircraft aircraft) {
        return aircraftService.updateAircraftById(id, aircraft);
    }

    @PatchMapping("/aircraft/{id}")
    public Aircraft partialUpdateAircraft(@PathVariable Long id, @RequestBody Aircraft aircraft) {
        return aircraftService.partialUpdateAircraft(id, aircraft);
    }

    @DeleteMapping("/aircraft/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Long id) {
        aircraftService.deleteAircraft(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
