package com.projet5safetynet.safetynet.controller;


import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.repository.DataRepository;
import com.projet5safetynet.safetynet.service.MedicalRecordService;
import com.projet5safetynet.safetynet.service.PersonService;

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
	@Autowired
	private MedicalRecordService medicalRecordService;

    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }
    
    @PostMapping
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord record) {
        medicalRecordService.addMedicalRecord(record);
        return ResponseEntity.status(HttpStatus.CREATED).body("Medical record added");
    }

    @PutMapping
    public ResponseEntity<String> updateMedicalRecord(@RequestBody MedicalRecord record) {
        boolean updated = medicalRecordService.updateMedicalRecord(record.getFirstName(),record.getLastName(),record);
        if (updated) {
            return ResponseEntity.ok("Medical record updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical record not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        boolean deleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if (deleted) {
            return ResponseEntity.ok("Medical record deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical record not found");
        }
    }
}
