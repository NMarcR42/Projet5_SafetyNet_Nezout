package com.projet5safetynet.safetynet.controller;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private static final Logger logger = LogManager.getLogger(FloodStationController.class);

    @Autowired
    private FloodService floodService;

    @GetMapping("/stations")
    public Map<String, List<FloodResidentDTO>> getResidentsByStations(@RequestParam List<String> stations) {
        logger.info("Requête reçue GET /flood/stations?stations={}", stations);
        Map<String, List<FloodResidentDTO>> response = floodService.getResidentsByStations(stations);
        logger.info("Réponse envoyée avec {} clés (stations)", response.size());
        return response;
    }
}
