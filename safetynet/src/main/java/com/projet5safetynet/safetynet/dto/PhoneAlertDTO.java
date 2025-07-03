package com.projet5safetynet.safetynet.dto;

//DTO pour l'endpoint /phoneAlert?firestation=X
//Retourne les numéros de téléphone des personnes couvertes
public class PhoneAlertDTO {
	private String phone;

    public PhoneAlertDTO(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
