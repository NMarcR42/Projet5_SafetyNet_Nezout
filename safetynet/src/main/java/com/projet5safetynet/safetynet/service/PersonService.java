package com.projet5safetynet.safetynet.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.Person;


import com.projet5safetynet.safetynet.repository.DataRepository;

@Service
public class PersonService {
	private static final Logger logger = LogManager.getLogger(PersonService.class);

    @Autowired
    private DataService dataService;

    public List<Person> getAllPersons() {
        logger.info("Récupération de toutes les personnes");
        return dataService.getDataBean().getPersons();
    }

    public void addPerson(Person person) {
        logger.info("Ajout d'une nouvelle personne : {} {}", person.getFirstName(), person.getLastName());
        List<Person> persons = dataService.getDataBean().getPersons();
        persons.add(person);
    }

    public boolean updatePerson(String firstName, String lastName, Person updatedPerson) {
        logger.info("Mise à jour de la personne : {} {}", firstName, lastName);
        List<Person> persons = dataService.getDataBean().getPersons();
        for (Person p : persons) {
            if (p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName)) {
                p.setAddress(updatedPerson.getAddress());
                p.setCity(updatedPerson.getCity());
                p.setZip(updatedPerson.getZip());
                p.setPhone(updatedPerson.getPhone());
                p.setEmail(updatedPerson.getEmail());
                logger.info("Mise à jour effectuée avec succès");
                return true;
            }
        }
        logger.warn("Personne non trouvée pour la mise à jour : {} {}", firstName, lastName);
        return false;
    }

    public boolean deletePerson(String firstName, String lastName) {
        logger.info("Suppression de la personne : {} {}", firstName, lastName);
        List<Person> persons = dataService.getDataBean().getPersons();
        boolean removed = persons.removeIf(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName));
        if (removed) {
            logger.info("Personne supprimée avec succès");
        } else {
            logger.warn("Personne non trouvée pour suppression : {} {}", firstName, lastName);
        }
        return removed;
    }
}
