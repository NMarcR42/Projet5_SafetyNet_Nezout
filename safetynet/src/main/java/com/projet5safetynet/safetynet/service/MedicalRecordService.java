package com.projet5safetynet.safetynet.service;

import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.repository.DataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
	@Autowired
	private DataService dataService;

    public List<MedicalRecord> getAllMedicalRecords() {
        return dataService.getDataBean().getMedicalrecords();
    }
    
    // Ajouter un dossier médical
    public void addMedicalRecord(MedicalRecord record) {
        List<MedicalRecord> records = dataService.getDataBean().getMedicalrecords();
        records.add(record);
    }

    // Mettre à jour un dossier médical
    public boolean updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedRecord) {
        List<MedicalRecord> records = dataService.getDataBean().getMedicalrecords();
        for (MedicalRecord r : records) {
            if (r.getFirstName().equalsIgnoreCase(firstName) && r.getLastName().equalsIgnoreCase(lastName)) {
                r.setBirthdate(updatedRecord.getBirthdate());
                r.setMedications(updatedRecord.getMedications());
                r.setAllergies(updatedRecord.getAllergies());
                return true;
            }
        }
        return false;
    }

    // Supprimer un dossier médical
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        List<MedicalRecord> records = dataService.getDataBean().getMedicalrecords();
        return records.removeIf(r -> r.getFirstName().equalsIgnoreCase(firstName) && r.getLastName().equalsIgnoreCase(lastName));
    }
}
