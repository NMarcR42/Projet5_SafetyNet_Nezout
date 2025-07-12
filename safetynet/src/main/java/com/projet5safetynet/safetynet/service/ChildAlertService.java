package com.projet5safetynet.safetynet.service;

import com.projet5safetynet.safetynet.dto.ChildAlertDTO;
import com.projet5safetynet.safetynet.dto.HouseholdMemberDTO;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.model.MedicalRecord;
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
public class ChildAlertService {
	private static final Logger logger = LogManager.getLogger(ChildAlertService.class);

    @Autowired
    private DataService dataService;

    public List<ChildAlertDTO> getChildrenByAddress(String address) {
        logger.info("Recherche des enfants à l'adresse : {}", address);

        List<Person> personsAtAddress = dataService.getDataBean().getPersons().stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());

        logger.info("Nombre total de personnes trouvées à l'adresse {} : {}", address, personsAtAddress.size());

        List<ChildAlertDTO> children = new ArrayList<>();

        for (Person person : personsAtAddress) {
            MedicalRecord record = dataService.getDataBean().getMedicalrecords().stream()
                    .filter(mr -> mr.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                                  mr.getLastName().equalsIgnoreCase(person.getLastName()))
                    .findFirst()
                    .orElse(null);

            if (record != null) {
                int age = getAge(record.getBirthdate());
                if (age <= 18) {
                    logger.info("Enfant trouvé : {} {} ({} ans)", person.getFirstName(), person.getLastName(), age);

                    List<HouseholdMemberDTO> others = personsAtAddress.stream()
                            .filter(p -> !(p.getFirstName().equalsIgnoreCase(person.getFirstName()) && p.getLastName().equalsIgnoreCase(person.getLastName())))
                            .map(p -> new HouseholdMemberDTO(p.getFirstName(), p.getLastName()))
                            .collect(Collectors.toList());

                    children.add(new ChildAlertDTO(person.getFirstName(), person.getLastName(), age, others));
                }
            } else {
                logger.warn("Aucun dossier médical trouvé pour {} {}", person.getFirstName(), person.getLastName());
            }
        }

        logger.info("Nombre total d'enfants trouvés à l'adresse {} : {}", address, children.size());
        return children;
    }

    private int getAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
