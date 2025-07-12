package com.projet5safetynet.safetynet.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet5safetynet.safetynet.dto.FloodResidentDTO;
import com.projet5safetynet.safetynet.model.Firestation;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.model.Person;

@Service
public class FloodService {
	private static final Logger logger = LogManager.getLogger(FloodService.class);

    @Autowired
    private DataService dataService;

    public Map<String, List<FloodResidentDTO>> getResidentsByStations(List<String> stations) {
        logger.info("Début de la récupération des résidents desservis par les stations : {}", stations);

        List<Firestation> firestations = dataService.getDataBean().getFirestations();
        List<Person> persons = dataService.getDataBean().getPersons();
        List<MedicalRecord> records = dataService.getDataBean().getMedicalrecords();

        List<String> addresses = firestations.stream()
            .filter(fs -> stations.contains(fs.getStation()))
            .map(Firestation::getAddress)
            .distinct()
            .collect(Collectors.toList());

        logger.info("Adresses desservies par les stations {} : {}", stations, addresses);

        Map<String, List<FloodResidentDTO>> result = new HashMap<>();

        for (String address : addresses) {
            List<Person> residentsAtAddress = persons.stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());

            List<FloodResidentDTO> dtos = new ArrayList<>();
            for (Person p : residentsAtAddress) {
                MedicalRecord record = records.stream()
                    .filter(r -> r.getFirstName().equalsIgnoreCase(p.getFirstName())
                              && r.getLastName().equalsIgnoreCase(p.getLastName()))
                    .findFirst()
                    .orElse(null);

                if (record != null) {
                    int age = calculateAge(record.getBirthdate());
                    dtos.add(new FloodResidentDTO(
                        p.getFirstName(),
                        p.getLastName(),
                        age,
                        p.getPhone(),
                        record.getMedications(),
                        record.getAllergies()
                    ));
                    logger.info("Ajout du résident {} {} ({} ans) à l'adresse {}", p.getFirstName(), p.getLastName(), age, address);
                } else {
                    logger.warn("Pas de dossier médical trouvé pour {} {} à l'adresse {}", p.getFirstName(), p.getLastName(), address);
                }
            }
            result.put(address, dtos);
        }

        logger.info("Fin récupération résidents par stations. Nombre d'adresses traitées : {}", result.size());
        return result;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birth = LocalDate.parse(birthdate, formatter);
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
