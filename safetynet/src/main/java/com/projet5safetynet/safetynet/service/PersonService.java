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
}
