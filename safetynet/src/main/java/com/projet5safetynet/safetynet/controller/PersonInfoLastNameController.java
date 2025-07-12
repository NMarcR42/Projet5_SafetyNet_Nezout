package com.projet5safetynet.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.projet5safetynet.safetynet.dto.PersonInfoLastNameDTO;
import com.projet5safetynet.safetynet.service.PersonInfoLastNameService;

@RestController
public class PersonInfoLastNameController {
	private static final Logger logger = LogManager.getLogger(PersonInfoLastNameController.class);

    @Autowired
    private PersonInfoLastNameService personInfoService;

    @GetMapping("/personInfolastName")
    public List<PersonInfoLastNameDTO> getPersonsByLastName(@RequestParam String lastName) {
        logger.info("GET /personInfolastName - Recherche des informations pour le nom : {}", lastName);
        return personInfoService.getPersonsByLastName(lastName);
    }
}
