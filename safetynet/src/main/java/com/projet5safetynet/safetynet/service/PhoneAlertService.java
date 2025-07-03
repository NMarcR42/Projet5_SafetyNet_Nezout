package com.projet5safetynet.safetynet.service;

import com.projet5safetynet.safetynet.dto.PhoneAlertDTO;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.model.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneAlertService {
	 @Autowired
    private DataService dataService;

    public List<PhoneAlertDTO> getPhoneNumbersByStation(String firestationNumber) {
        List<String> addresses = dataService.getDataBean().getFirestations().stream()
                .filter(f -> f.getStation().equals(firestationNumber))
                .map(Firestation::getAddress)
                .collect(Collectors.toList());

        return dataService.getDataBean().getPersons().stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .map(p -> new PhoneAlertDTO(p.getPhone()))
                .distinct()
                .collect(Collectors.toList());
    }
}
