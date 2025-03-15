package com.bridge.example.aircraft.controller;

import com.bridge.example.aircraft.entity.Pilot;
import com.bridge.example.aircraft.service.PilotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pilot")
public class PilotController {

    private final PilotService pilotService;

    public PilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @PostMapping
    public ResponseEntity<Pilot> createPilot(@RequestBody Pilot pilot) {
        pilotService.savePilot(pilot);
        return new ResponseEntity<>(pilot, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Pilot> getAllPilots() {
        return pilotService.findAllPilots();
    }

    @GetMapping("/{pilotId}")
    public Pilot getSinglePilot(@PathVariable Long pilotId) {
        return pilotService.findPilotById(pilotId);
    }
}
