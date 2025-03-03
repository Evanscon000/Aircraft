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

    public ArrayList<Aircraft> findAllAircraft() {
        Aircraft aircraft = new Aircraft(1L, "Dog Hourse", "Snoopy, the Beagle");
        aircraftList.add(aircraft);
        System.out.println("List all aircraft");
        return aircraftList;
    }

    public Aircraft findAircraftById(Long id) {
        Aircraft aircraft = new Aircraft(1L, "Dog Hourse", "Snoopy, the Beagle");
        aircraftList.add(aircraft);
        System.out.println("Found Dog House: " + id);
        return aircraft;
    }

}
