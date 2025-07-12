package com.projet5safetynet.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet5safetynet.safetynet.model.Person;

@Service
public class CommunityEmailService {
	private static final Logger logger = LogManager.getLogger(CommunityEmailService.class);

    @Autowired
    private DataService dataService;

    public List<String> getEmailsByCity(String city) {
        logger.info("Recherche des emails dans la ville : {}", city);
        List<String> emails = dataService.getDataBean().getPersons().stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .distinct()
                .collect(Collectors.toList());
        logger.info("Nombre d'emails trouvés dans la ville {} : {}", city, emails.size());
        return emails;
    }
}
