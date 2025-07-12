package com.projet5safetynet.safetynet.controller;


import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.repository.DataRepository;
import com.projet5safetynet.safetynet.service.FirestationService;
import com.projet5safetynet.safetynet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
	private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> getAllPersons() {
        logger.info("GET /person - récupération de toutes les personnes");
        return personService.getAllPersons();
    }

    @PostMapping
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        logger.info("POST /person - ajout d'une personne : {}", person);
        personService.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body("Person added");
    }

    @PutMapping
    public ResponseEntity<String> updatePerson(@RequestBody Person person) {
        logger.info("PUT /person - mise à jour de la personne : {}", person);
        boolean updated = personService.updatePerson(person.getFirstName(), person.getLastName(), person);
        if (updated) {
            logger.info("Personne mise à jour avec succès");
            return ResponseEntity.ok("Person updated");
        } else {
            logger.warn("Personne non trouvée pour mise à jour");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("DELETE /person - suppression de la personne : {} {}", firstName, lastName);
        boolean deleted = personService.deletePerson(firstName, lastName);
        if (deleted) {
            logger.info("Personne supprimée avec succès");
            return ResponseEntity.ok("Person deleted");
        } else {
            logger.warn("Personne non trouvée pour suppression");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
    }
    
}
