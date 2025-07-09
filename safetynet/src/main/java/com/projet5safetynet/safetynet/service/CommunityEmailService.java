package com.projet5safetynet.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet5safetynet.safetynet.model.Person;

@Service
public class CommunityEmailService {
	@Autowired
    private DataService dataService;

    public List<String> getEmailsByCity(String city) {
        return dataService.getDataBean().getPersons().stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .distinct()
                .collect(Collectors.toList());
    }
}
