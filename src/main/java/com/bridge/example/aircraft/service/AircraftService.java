package com.bridge.example.aircraft.service;

import com.bridge.example.aircraft.entity.Aircraft;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AircraftService {

    private ArrayList<Aircraft> aircraftList = new ArrayList<Aircraft>();

    public Aircraft saveAircraft(Aircraft aircraft) {
        System.out.println("Saved new aircraft: " + aircraft.getAirframe() + " pilot:  " + aircraft.getPilot());
        aircraftList.add(aircraft);
        return aircraft;
    }

}
