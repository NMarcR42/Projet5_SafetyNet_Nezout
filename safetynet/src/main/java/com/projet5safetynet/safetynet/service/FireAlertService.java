package com.projet5safetynet.safetynet.service;

import com.projet5safetynet.safetynet.dto.FireResidentDTO;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.model.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireAlertService {
	@Autowired
    private DataService dataService;

    public List<FireResidentDTO> getResidentsByAddress(String address) {
        List<Person> residents = dataService.getDataBean().getPersons().stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());

        List<FireResidentDTO> result = new ArrayList<>();

        for (Person person : residents) {
            MedicalRecord record = dataService.getDataBean().getMedicalrecords().stream()
                    .filter(m -> m.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                                 m.getLastName().equalsIgnoreCase(person.getLastName()))
                    .findFirst()
                    .orElse(null);

            if (record != null) {
                int age = getAge(record.getBirthdate());
                result.add(new FireResidentDTO(
                        person.getFirstName(),
                        person.getLastName(),
                        age,
                        person.getPhone(),
                        record.getMedications(),
                        record.getAllergies()
                ));
            }
        }

        return result;
    }

    private int getAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
