package com.projet5safetynet.safetynet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Firestation {
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("station")
    private String station;

	public Firestation() {
	    // constructeur par défaut requis pour la désérialisation JSON
	}
	
	public Firestation(String address, String station) {
        this.address = address;
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
