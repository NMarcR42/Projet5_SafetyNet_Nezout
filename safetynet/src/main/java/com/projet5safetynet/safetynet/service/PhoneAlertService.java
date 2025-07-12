package com.projet5safetynet.safetynet.service;

import com.projet5safetynet.safetynet.dto.PhoneAlertDTO;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.model.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PhoneAlertService {
	 private static final Logger logger = LoggerFactory.getLogger(PhoneAlertService.class);

	    @Autowired
	    private DataService dataService;

	    public List<PhoneAlertDTO> getPhoneNumbersByStation(String firestationNumber) {
	        logger.info("Recherche des numéros de téléphone pour la caserne numéro : {}", firestationNumber);

	        List<String> addresses = dataService.getDataBean().getFirestations().stream()
	                .filter(f -> f.getStation().equals(firestationNumber))
	                .map(Firestation::getAddress)
	                .collect(Collectors.toList());

	        logger.debug("Adresses récupérées pour la caserne {} : {}", firestationNumber, addresses);

	        List<PhoneAlertDTO> phones = dataService.getDataBean().getPersons().stream()
	                .filter(p -> addresses.contains(p.getAddress()))
	                .map(p -> new PhoneAlertDTO(p.getPhone()))
	                .distinct()
	                .collect(Collectors.toList());

	        logger.info("Nombre de téléphones trouvés : {}", phones.size());

	        return phones;
	    }
}
