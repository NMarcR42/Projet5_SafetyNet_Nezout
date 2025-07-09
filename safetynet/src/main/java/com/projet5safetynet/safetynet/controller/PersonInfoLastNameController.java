package com.projet5safetynet.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projet5safetynet.safetynet.dto.PersonInfoLastNameDTO;
import com.projet5safetynet.safetynet.service.PersonInfoLastNameService;

@RestController
public class PersonInfoLastNameController {
	@Autowired
    private PersonInfoLastNameService personInfoService;

    @GetMapping("/personInfolastName")
    public List<PersonInfoLastNameDTO> getPersonsByLastName(@RequestParam String lastName) {
        return personInfoService.getPersonsByLastName(lastName);
    }
}
