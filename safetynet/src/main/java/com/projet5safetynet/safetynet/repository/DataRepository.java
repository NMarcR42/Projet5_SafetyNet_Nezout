package com.projet5safetynet.safetynet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.Firestation;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.service.DataService;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Repository
public class DataRepository {
	

    @Autowired
    private DataService dataService;
    
    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = new ClassPathResource("data.json").getInputStream();
            DataBean data = mapper.readValue(is, DataBean.class);
            dataService.setDataBean(data);
            
            System.out.println("✅ Données chargées depuis data.json !");
        } catch (Exception e) {
            System.err.println("❌ Erreur de chargement des données : " + e.getMessage());
        }
    }
}
