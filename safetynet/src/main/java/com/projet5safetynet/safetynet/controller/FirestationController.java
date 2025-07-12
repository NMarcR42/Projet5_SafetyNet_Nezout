package com.projet5safetynet.safetynet.controller;

import com.projet5safetynet.safetynet.model.Firestation;
import com.projet5safetynet.safetynet.repository.DataRepository;
import com.projet5safetynet.safetynet.service.FirestationService;
import com.projet5safetynet.safetynet.dto.FirestationCoverageDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/firestation")
public class FirestationController {
	private static final Logger logger = LogManager.getLogger(FirestationController.class);

    @Autowired
    private FirestationService firestationService;
    
    @GetMapping("/all")
    public List<Firestation> getAllFirestations() {
        logger.info("Requête reçue GET /firestation/all");
        List<Firestation> firestations = firestationService.getAllFirestations();
        logger.info("Réponse : {} firestation(s) trouvée(s)", firestations.size());
        return firestations;
    }

    @GetMapping
    public FirestationCoverageDTO getCoverage(@RequestParam String stationNumber) {
        logger.info("Requête GET /firestation?stationNumber={} reçue", stationNumber);
        FirestationCoverageDTO response = firestationService.getPersonsByStation(stationNumber);
        logger.info("Réponse envoyée: {} personnes, {} adultes, {} enfants", 
            response.getPersons().size(), response.getAdultCount(), response.getChildCount());
        return response;
    }
    
    @PostMapping
    public ResponseEntity<String> addFirestationMapping(@RequestBody Firestation firestation) {
        logger.info("Requête POST /firestation avec payload: {}", firestation);
        firestationService.addFirestation(firestation);
        logger.info("Mapping ajouté pour l'adresse: {}", firestation.getAddress());
        return ResponseEntity.status(HttpStatus.CREATED).body("Mapping added");
    }

    @PutMapping
    public ResponseEntity<String> updateFirestationMapping(@RequestBody Firestation firestation) {
        logger.info("Requête PUT /firestation avec payload: {}", firestation);
        boolean updated = firestationService.updateFirestation(firestation.getAddress(), firestation.getStation());
        if (updated) {
            logger.info("Mapping mis à jour pour l'adresse: {}", firestation.getAddress());
            return ResponseEntity.ok("Mapping updated");
        } else {
            logger.warn("Mapping non trouvé pour l'adresse: {}", firestation.getAddress());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mapping not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFirestationMapping(@RequestParam String address, @RequestParam(required = false) String stationNumber) {
        logger.info("Requête DELETE /firestation avec address={} et stationNumber={}", address, stationNumber);
        boolean deleted = firestationService.deleteFirestation(address, stationNumber);
        if (deleted) {
            logger.info("Mapping supprimé pour l'adresse: {}", address);
            return ResponseEntity.ok("Mapping deleted");
        } else {
            logger.warn("Mapping non trouvé pour suppression, adresse: {}", address);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mapping not found");
        }
    }
}
