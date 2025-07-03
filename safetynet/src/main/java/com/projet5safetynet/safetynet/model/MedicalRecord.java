package com.projet5safetynet.safetynet.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MedicalRecord {
	@JsonProperty("firstName")
    private String firstName;
	
	@JsonProperty("lastName")
    private String lastName;
	
	@JsonProperty("birthdate")
    private String birthdate;
	
	@JsonProperty("medications")
    private List<String> medications;
	
	@JsonProperty("allergies")
    private List<String> allergies;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public List<String> getMedications() {
		return medications;
	}

	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
}
