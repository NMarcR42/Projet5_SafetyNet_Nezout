package com.projet5safetynet.safetynet.dto;

import java.util.List;

//DTO pour l'endpoint /childAlert?address=X
//Contient les enfants + les autres membres du foyer
public class ChildAlertDTO {
	private String firstName;
    private String lastName;
    private int age;
    private List<HouseholdMemberDTO> otherHouseholdMembers;

    public ChildAlertDTO(String firstName, String lastName, int age, List<HouseholdMemberDTO> otherHouseholdMembers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.otherHouseholdMembers = otherHouseholdMembers;
    }

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

    public int getAge() { 
    	return age; 
	}
    public void setAge(int age) { 
    	this.age = age; 
	}

    public List<HouseholdMemberDTO> getOtherHouseholdMembers() { 
    	return otherHouseholdMembers; 
	}
    public void setOtherHouseholdMembers(List<HouseholdMemberDTO> otherHouseholdMembers) { 
    	this.otherHouseholdMembers = otherHouseholdMembers; 
	}
}
