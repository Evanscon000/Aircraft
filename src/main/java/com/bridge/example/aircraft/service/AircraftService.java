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

}
