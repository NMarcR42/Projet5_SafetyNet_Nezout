package com.projet5safetynet.safetynet.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet5safetynet.safetynet.dto.PersonInfoLastNameDTO;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.model.Person;

@Service
public class PersonInfoLastNameService {
	private static final Logger logger = LogManager.getLogger(PersonInfoLastNameService.class);

    @Autowired
    private DataService dataService;

    public List<PersonInfoLastNameDTO> getPersonsByLastName(String lastName) {
        logger.info("Recherche des personnes avec le nom de famille : {}", lastName);

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
                    logger.info("Ajout de la personne {} {}, {} ans, adresse: {}", p.getFirstName(), p.getLastName(), age, p.getAddress());
                } else {
                    logger.warn("Pas de dossier médical trouvé pour {} {}", p.getFirstName(), p.getLastName());
                }
            }
        }

        logger.info("Fin de la recherche, nombre de personnes trouvées : {}", result.size());
        return result;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
