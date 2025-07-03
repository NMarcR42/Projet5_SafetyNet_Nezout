package com.projet5safetynet.safetynet.dto;

import lombok.Data;
import java.util.List;

//DTO utilisé pour retourner les informations de couverture d'une station de pompier
//dans l'endpoint /firestation?stationNumber=<X>
public class FirestationCoverageDTO {
	private List<PersonCoverageInfo> persons;
    private int adultCount;
    private int childCount;
    
	public List<PersonCoverageInfo> getPersons() {
		return persons;
	}
	public void setPersons(List<PersonCoverageInfo> persons) {
		this.persons = persons;
	}
	public int getAdultCount() {
		return adultCount;
	}
	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}
	public int getChildCount() {
		return childCount;
	}
	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}
	
}
