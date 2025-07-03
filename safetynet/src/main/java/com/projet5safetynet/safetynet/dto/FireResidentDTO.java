package com.projet5safetynet.safetynet.dto;

import java.util.List;

//DTO pour /fire?address=X
//Retourne tous les résidents + station de pompier + info santé
public class FireResidentDTO {
	private String firstName;
    private String lastName;
    private int age;
    private String phone;
    private List<String> medications;
    private List<String> allergies;

    public FireResidentDTO(String firstName, String lastName, int age, String phone, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phone = phone;
        this.medications = medications;
        this.allergies = allergies;
    }

    public String getFirstName() { 
    	return firstName; 
	}
    
    public String getLastName() { 
    	return lastName; 
	
    }
    
    public int getAge() { 
    	return age; 
	}
    
    public String getPhone() { 
    	return phone; 
	}
    
    public List<String> getMedications() { 
    	return medications; 
	}
    
    public List<String> getAllergies() { 
    	return allergies; 
	}

    public void setFirstName(String firstName) { 
    	this.firstName = firstName; 
    }
    
    public void setLastName(String lastName) { 
    	this.lastName = lastName; 
    }
    
    public void setAge(int age) { 
    	this.age = age; 
	}
    public void setPhone(String phone) { 
    	this.phone = phone; 
	}
    
    public void setMedications(List<String> medications) { 
    	this.medications = medications; 
	}
    
    public void setAllergies(List<String> allergies) { 
    	this.allergies = allergies; 
	}
}
