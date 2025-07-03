package com.projet5safetynet.safetynet.service;

import com.projet5safetynet.safetynet.dto.FirestationCoverageDTO;
import com.projet5safetynet.safetynet.dto.PersonCoverageInfo;
import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.Firestation;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.repository.DataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirestationService {
	@Autowired
	private DataService dataService;

    public List<Firestation> getAllFirestations() {
        return dataService.getDataBean().getFirestations();
    }
    
    public FirestationCoverageDTO getPersonsByStation(String stationNumber) {
        List<Firestation> firestations = dataService.getDataBean().getFirestations();
        List<Person> persons = dataService.getDataBean().getPersons();
        List<MedicalRecord> records = dataService.getDataBean().getMedicalrecords();

        List<String> addresses = firestations.stream()
                .filter(f -> f.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .collect(Collectors.toList());
        

        List<PersonCoverageInfo> coveredPersons = persons.stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .map(p -> new PersonCoverageInfo(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone()))
                .collect(Collectors.toList());
        System.out.println("Station demandée: " + stationNumber);
        System.out.println("Adresses couvertes: " + addresses);
        int childCount = 0;
        int adultCount = 0;

        for (Person p : persons) {
            if (addresses.contains(p.getAddress())) {
                MedicalRecord record = records.stream()
                        .filter(r -> r.getFirstName().equals(p.getFirstName()) && r.getLastName().equals(p.getLastName()))
                        .findFirst()
                        .orElse(null);
                if (record != null && isChild(record.getBirthdate())) {
                    childCount++;
                } else {
                    adultCount++;
                }
            }
        }

        FirestationCoverageDTO dto = new FirestationCoverageDTO();
        dto.setPersons(coveredPersons);
        dto.setAdultCount(adultCount);
        dto.setChildCount(childCount);

        return dto;
    }

    private boolean isChild(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears() <= 18;
    }
}
