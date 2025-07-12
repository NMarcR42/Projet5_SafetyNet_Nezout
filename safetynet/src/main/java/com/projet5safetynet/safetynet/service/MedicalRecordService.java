package com.projet5safetynet.safetynet.service;

import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.repository.DataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
	private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);

    @Autowired
    private DataService dataService;

    public List<MedicalRecord> getAllMedicalRecords() {
        logger.info("Récupération de tous les dossiers médicaux");
        List<MedicalRecord> records = dataService.getDataBean().getMedicalrecords();
        logger.info("Nombre de dossiers médicaux trouvés : {}", records.size());
        return records;
    }

    // Ajouter un dossier médical
    public void addMedicalRecord(MedicalRecord record) {
        logger.info("Ajout d'un dossier médical pour {} {}", record.getFirstName(), record.getLastName());
        List<MedicalRecord> records = dataService.getDataBean().getMedicalrecords();
        records.add(record);
        logger.info("Dossier ajouté. Nombre total de dossiers médicaux : {}", records.size());
    }

    // Mettre à jour un dossier médical
    public boolean updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedRecord) {
        logger.info("Mise à jour du dossier médical pour {} {}", firstName, lastName);
        List<MedicalRecord> records = dataService.getDataBean().getMedicalrecords();
        for (MedicalRecord r : records) {
            if (r.getFirstName().equalsIgnoreCase(firstName) && r.getLastName().equalsIgnoreCase(lastName)) {
                r.setBirthdate(updatedRecord.getBirthdate());
                r.setMedications(updatedRecord.getMedications());
                r.setAllergies(updatedRecord.getAllergies());
                logger.info("Dossier médical mis à jour pour {} {}", firstName, lastName);
                return true;
            }
        }
        logger.warn("Aucun dossier médical trouvé pour {} {}", firstName, lastName);
        return false;
    }

    // Supprimer un dossier médical
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        logger.info("Suppression du dossier médical pour {} {}", firstName, lastName);
        List<MedicalRecord> records = dataService.getDataBean().getMedicalrecords();
        boolean removed = records.removeIf(r -> r.getFirstName().equalsIgnoreCase(firstName) && r.getLastName().equalsIgnoreCase(lastName));
        if (removed) {
            logger.info("Dossier médical supprimé pour {} {}", firstName, lastName);
        } else {
            logger.warn("Aucun dossier médical trouvé à supprimer pour {} {}", firstName, lastName);
        }
        return removed;
    }
}
