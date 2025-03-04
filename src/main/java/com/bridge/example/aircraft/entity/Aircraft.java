package com.bridge.example.aircraft.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String airframe;
    private String pilot;

    public Aircraft() {
    }

    public Aircraft(String airframe, String pilot) {
        this.airframe = airframe;
        this.pilot = pilot;
    }

    public Long getId() {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getAirframe() {
        return airframe;
    }

    public void setAirframe(String airframe) {
        this.airframe = airframe;
    }

    public String getPilot() {
        return pilot;
    }

    public void setPilot(String pilot) {
        this.pilot = pilot;
    }
}
