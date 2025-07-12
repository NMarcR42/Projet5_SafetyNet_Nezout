package com.projet5safetynet.safetynet.service;

import com.projet5safetynet.safetynet.dto.FireResidentDTO;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.model.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireAlertService {
	private static final Logger logger = LogManager.getLogger(FireAlertService.class);

    @Autowired
    private DataService dataService;

    public List<FireResidentDTO> getResidentsByAddress(String address) {
        logger.info("Recherche des résidents à l'adresse : {}", address);

        List<Person> residents = dataService.getDataBean().getPersons().stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());

        logger.info("Nombre de résidents trouvés à l'adresse {} : {}", address, residents.size());

        List<FireResidentDTO> result = new ArrayList<>();

        for (Person person : residents) {
            MedicalRecord record = dataService.getDataBean().getMedicalrecords().stream()
                    .filter(m -> m.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                                 m.getLastName().equalsIgnoreCase(person.getLastName()))
                    .findFirst()
                    .orElse(null);

            if (record != null) {
                int age = getAge(record.getBirthdate());
                logger.info("Résident trouvé : {} {}, âge {}", person.getFirstName(), person.getLastName(), age);

                result.add(new FireResidentDTO(
                        person.getFirstName(),
                        person.getLastName(),
                        age,
                        person.getPhone(),
                        record.getMedications(),
                        record.getAllergies()
                ));
            } else {
                logger.warn("Aucun dossier médical trouvé pour {} {}", person.getFirstName(), person.getLastName());
            }
        }

        logger.info("Nombre total de résidents avec dossier médical à l'adresse {} : {}", address, result.size());
        return result;
    }

    private int getAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
