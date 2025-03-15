package com.bridge.example.aircraft.service;

import com.bridge.example.aircraft.entity.Pilot;
import com.bridge.example.aircraft.repository.PilotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PilotService {

    private PilotRepository pilotRepository;

    public Pilot savePilot(Pilot pilot) {
        return pilotRepository.save(pilot);
    }

    public List<Pilot> findAllPilots() {
        return pilotRepository.findAll();
    }

    public Pilot findPilotById(Long id) {
        return pilotRepository.findById(id).get();
    }

}
