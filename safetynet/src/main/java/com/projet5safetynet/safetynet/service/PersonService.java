package com.projet5safetynet.safetynet.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.Person;


import com.projet5safetynet.safetynet.repository.DataRepository;

@Service
public class PersonService {
	@Autowired
	private DataService dataService;
	
    public List<Person> getAllPersons() {
        return dataService.getDataBean().getPersons();
    }
    
    // Ajouter une personne
    public void addPerson(Person person) {
        List<Person> persons = dataService.getDataBean().getPersons();
        persons.add(person);
    }

    // Mettre à jour une personne (sauf prénom/nom)
    public boolean updatePerson(String firstName, String lastName, Person updatedPerson) {
        List<Person> persons = dataService.getDataBean().getPersons();
        for (Person p : persons) {
            if (p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName)) {
                p.setAddress(updatedPerson.getAddress());
                p.setCity(updatedPerson.getCity());
                p.setZip(updatedPerson.getZip());
                p.setPhone(updatedPerson.getPhone());
                p.setEmail(updatedPerson.getEmail());
                return true;
            }
        }
        return false; // personne non trouvée
    }

    // Supprimer une personne
    public boolean deletePerson(String firstName, String lastName) {
        List<Person> persons = dataService.getDataBean().getPersons();
        return persons.removeIf(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName));
    }
}
