package com.projet5safetynet.safetynet.service;

import com.projet5safetynet.safetynet.dto.ChildAlertDTO;
import com.projet5safetynet.safetynet.dto.HouseholdMemberDTO;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildAlertService {
	@Autowired
    private DataService dataService;

    public List<ChildAlertDTO> getChildrenByAddress(String address) {
        List<Person> personsAtAddress = dataService.getDataBean().getPersons().stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());

        List<ChildAlertDTO> children = new ArrayList<>();

        for (Person person : personsAtAddress) {
            MedicalRecord record = dataService.getDataBean().getMedicalrecords().stream()
                    .filter(mr -> mr.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                                  mr.getLastName().equalsIgnoreCase(person.getLastName()))
                    .findFirst()
                    .orElse(null);

            if (record != null && getAge(record.getBirthdate()) <= 18) {
                List<HouseholdMemberDTO> others = personsAtAddress.stream()
                        .filter(p -> !(p.getFirstName().equalsIgnoreCase(person.getFirstName()) && p.getLastName().equalsIgnoreCase(person.getLastName())))
                        .map(p -> new HouseholdMemberDTO(p.getFirstName(), p.getLastName()))
                        .collect(Collectors.toList());

                children.add(new ChildAlertDTO(person.getFirstName(), person.getLastName(), getAge(record.getBirthdate()), others));
            }
        }

        return children;
    }

    private int getAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
