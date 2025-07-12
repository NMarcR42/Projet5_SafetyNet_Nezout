package com.projet5safetynet.safetynet.controller;


import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.repository.DataRepository;
import com.projet5safetynet.safetynet.service.MedicalRecordService;
import com.projet5safetynet.safetynet.service.PersonService;
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
@RequestMapping("/medicalrecord")
public class MedicalRecordController {
	private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        logger.info("Requête reçue GET /medicalrecord");
        List<MedicalRecord> response = medicalRecordService.getAllMedicalRecords();
        logger.info("Réponse : {} dossier(s) médical(aux) trouvé(s)", response.size());
        return response;
    }

    @PostMapping
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord record) {
        logger.info("Requête POST /medicalrecord avec record: {} {}", record.getFirstName(), record.getLastName());
        medicalRecordService.addMedicalRecord(record);
        logger.info("Medical record ajouté pour {} {}", record.getFirstName(), record.getLastName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Medical record added");
    }

    @PutMapping
    public ResponseEntity<String> updateMedicalRecord(@RequestBody MedicalRecord record) {
        logger.info("Requête PUT /medicalrecord avec record: {} {}", record.getFirstName(), record.getLastName());
        boolean updated = medicalRecordService.updateMedicalRecord(record.getFirstName(), record.getLastName(), record);
        if (updated) {
            logger.info("Medical record mis à jour pour {} {}", record.getFirstName(), record.getLastName());
            return ResponseEntity.ok("Medical record updated");
        } else {
            logger.warn("Medical record non trouvé pour mise à jour : {} {}", record.getFirstName(), record.getLastName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical record not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("Requête DELETE /medicalrecord?firstName={}&lastName={}", firstName, lastName);
        boolean deleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if (deleted) {
            logger.info("Medical record supprimé pour {} {}", firstName, lastName);
            return ResponseEntity.ok("Medical record deleted");
        } else {
            logger.warn("Medical record non trouvé pour suppression : {} {}", firstName, lastName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical record not found");
        }
    }
}
