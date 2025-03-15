package com.bridge.example.aircraft.service;

import com.bridge.example.aircraft.entity.Aircraft;
import com.bridge.example.aircraft.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftService {

    @Autowired
    private AircraftRepository aircraftRepository;

    public Aircraft saveAircraft(Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    public List<Aircraft> findAllAircraft() {
        return aircraftRepository.findAll();
    }

    public Aircraft findAircraftById(Long id) {
        return aircraftRepository.findById(id).get();
    }

    public Aircraft updateAircraftById(Long id, Aircraft aircraft) {
        if (!aircraftRepository.existsById(id)) {
            // should return status code not found
            System.out.println("Aircraft not found with id: " + id);
        }
        aircraft.setId(id);
        return aircraftRepository.save(aircraft);
    }

    public Aircraft partialUpdateAircraft(Long id, Aircraft aircraft) {
        Aircraft existingAircraft = aircraftRepository.findById(id).orElse(null);
        if (existingAircraft == null) {
            // Send back not found status code
            System.out.println("Aircraft not found with id " + id);
        }

        if (aircraft.getAirframe() != null) {
            existingAircraft.setAirframe(aircraft.getAirframe());
        }
        if (aircraft.getPilot() != null) {
            existingAircraft.setPilot(aircraft.getPilot());
        }

        return aircraftRepository.save(existingAircraft);
    }

    public void deleteAircraft(Long id) {
        if (!aircraftRepository.existsById(id)) {
            System.out.println("Aircraft not found with id " + id);
        }
        // Send back Delete status success
        aircraftRepository.deleteById(id);
    }
}
