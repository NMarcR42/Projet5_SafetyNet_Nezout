package com.projet5safetynet.safetynet.controller;


import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.repository.DataRepository;
import com.projet5safetynet.safetynet.service.FirestationService;
import com.projet5safetynet.safetynet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
	@Autowired
	private PersonService personService;

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }
    
    @PostMapping
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        personService.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body("Person added");
    }

    @PutMapping
    public ResponseEntity<String> updatePerson(@RequestBody Person person) {
        boolean updated = personService.updatePerson(person.getFirstName(),person.getLastName(), person);
        if (updated) {
            return ResponseEntity.ok("Person updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        boolean deleted = personService.deletePerson(firstName, lastName);
        if (deleted) {
            return ResponseEntity.ok("Person deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
    }
    
}
