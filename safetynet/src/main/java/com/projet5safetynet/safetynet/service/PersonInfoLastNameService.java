package com.projet5safetynet.safetynet.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet5safetynet.safetynet.dto.PersonInfoLastNameDTO;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.model.Person;

@Service
public class PersonInfoLastNameService {
	@Autowired
    private DataService dataService;

    public List<PersonInfoLastNameDTO> getPersonsByLastName(String lastName) {
        List<Person> persons = dataService.getDataBean().getPersons();
        List<MedicalRecord> records = dataService.getDataBean().getMedicalrecords();

        List<PersonInfoLastNameDTO> result = new ArrayList<>();

        for (Person p : persons) {
            if (p.getLastName().equalsIgnoreCase(lastName)) {
                MedicalRecord record = records.stream()
                    .filter(r -> r.getFirstName().equalsIgnoreCase(p.getFirstName())
                              && r.getLastName().equalsIgnoreCase(p.getLastName()))
                    .findFirst()
                    .orElse(null);

                if (record != null) {
                    int age = calculateAge(record.getBirthdate());
                    result.add(new PersonInfoLastNameDTO(
                        p.getFirstName(),
                        p.getLastName(),
                        p.getAddress(),
                        age,
                        p.getEmail(),
                        record.getMedications(),
                        record.getAllergies()
                    ));
                }
            }
        }
        return result;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
