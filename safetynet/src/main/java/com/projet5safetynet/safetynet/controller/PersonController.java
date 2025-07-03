package com.projet5safetynet.safetynet.controller;


import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.repository.DataRepository;
import com.projet5safetynet.safetynet.service.FirestationService;
import com.projet5safetynet.safetynet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
	@Autowired
	private PersonService personService;

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }
    
}
