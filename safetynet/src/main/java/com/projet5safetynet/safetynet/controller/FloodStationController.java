package com.projet5safetynet.safetynet.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projet5safetynet.safetynet.dto.FloodResidentDTO;
import com.projet5safetynet.safetynet.service.FloodService;

@RestController
@RequestMapping("/flood")
public class FloodStationController {
	@Autowired
    private FloodService floodService;

    @GetMapping("/stations")
    public Map<String, List<FloodResidentDTO>> getResidentsByStations(@RequestParam List<String> stations) {
        // clé = adresse, valeur = liste des habitants à cette adresse
        return floodService.getResidentsByStations(stations);
    }
}
