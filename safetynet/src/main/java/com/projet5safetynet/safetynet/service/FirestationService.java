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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirestationService {
	private static final Logger logger = LogManager.getLogger(FirestationService.class);

    @Autowired
    private DataService dataService;

    public List<Firestation> getAllFirestations() {
        logger.info("Récupération de toutes les casernes");
        List<Firestation> firestations = dataService.getDataBean().getFirestations();
        logger.info("Nombre de casernes trouvées : {}", firestations.size());
        return firestations;
    }

    public FirestationCoverageDTO getPersonsByStation(String stationNumber) {
        logger.info("Requête de personnes couvertes par la caserne numéro : {}", stationNumber);

        List<Firestation> firestations = dataService.getDataBean().getFirestations();
        List<Person> persons = dataService.getDataBean().getPersons();
        List<MedicalRecord> records = dataService.getDataBean().getMedicalrecords();

        List<String> addresses = firestations.stream()
                .filter(f -> f.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .collect(Collectors.toList());

        logger.info("Adresses couvertes par la caserne {} : {}", stationNumber, addresses);

        List<PersonCoverageInfo> coveredPersons = persons.stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .map(p -> new PersonCoverageInfo(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone()))
                .collect(Collectors.toList());

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

        logger.info("Nombre d'enfants couverts : {}", childCount);
        logger.info("Nombre d'adultes couverts : {}", adultCount);

        FirestationCoverageDTO dto = new FirestationCoverageDTO();
        dto.setPersons(coveredPersons);
        dto.setAdultCount(adultCount);
        dto.setChildCount(childCount);

        return dto;
    }

    private boolean isChild(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            LocalDate birth = LocalDate.parse(birthdate, formatter);
            int age = Period.between(birth, LocalDate.now()).getYears();
            logger.info("Calcul de l'âge pour la date de naissance {} : {} ans", birthdate, age);
            return age <= 18;
        } catch (Exception e) {
            logger.error("Erreur lors du parsing de la date de naissance: {}", birthdate, e);
            // En cas d'erreur, considérer adulte par défaut
            return false;
        }
    }

    public void addFirestation(Firestation firestation) {
        logger.info("Ajout d'une nouvelle caserne avec adresse {} et numéro {}", firestation.getAddress(), firestation.getStation());
        List<Firestation> firestations = dataService.getDataBean().getFirestations();
        firestations.add(firestation);
        logger.info("Caserne ajoutée. Nombre total de casernes : {}", firestations.size());
    }

    public boolean updateFirestation(String address, String newStationNumber) {
        logger.info("Mise à jour de la caserne pour l'adresse {} avec le nouveau numéro {}", address, newStationNumber);
        List<Firestation> firestations = dataService.getDataBean().getFirestations();
        for (Firestation f : firestations) {
            if (f.getAddress().equalsIgnoreCase(address)) {
                String oldStation = f.getStation();
                f.setStation(newStationNumber);
                logger.info("Caserne mise à jour de {} à {} pour l'adresse {}", oldStation, newStationNumber, address);
                return true;
            }
        }
        logger.warn("Aucune caserne trouvée pour l'adresse {}", address);
        return false;
    }

    public boolean deleteFirestation(String address, String stationNumber) {
        logger.info("Suppression d'une caserne avec adresse = {} ou station = {}", address, stationNumber);
        List<Firestation> firestations = dataService.getDataBean().getFirestations();
        boolean removed = firestations.removeIf(f -> 
            (address != null && f.getAddress().equalsIgnoreCase(address)) ||
            (stationNumber != null && f.getStation().equalsIgnoreCase(stationNumber))
        );
        if (removed) {
            logger.info("Une ou plusieurs casernes ont été supprimées.");
        } else {
            logger.warn("Aucune caserne correspondante trouvée pour suppression.");
        }
        return removed;
    }
}
