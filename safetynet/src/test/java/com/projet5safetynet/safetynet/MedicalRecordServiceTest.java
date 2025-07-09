package com.projet5safetynet.safetynet;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.service.DataService;
import com.projet5safetynet.safetynet.service.MedicalRecordService;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {
	@Mock
    private DataService dataService;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    private DataBean dataBean;

    @BeforeEach
    void setup() {
        dataBean = new DataBean();
        dataBean.setMedicalrecords(new ArrayList<>());
        when(dataService.getDataBean()).thenReturn(dataBean);
    }

    @Test
    void testAddAndGetAllMedicalRecords() {
        MedicalRecord record = new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1"), List.of("allergy1"));
        medicalRecordService.addMedicalRecord(record);

        List<MedicalRecord> records = medicalRecordService.getAllMedicalRecords();
        Assertions.assertEquals(1, records.size());
        Assertions.assertEquals("John", records.get(0).getFirstName());
    }

    @Test
    void testUpdateMedicalRecordSuccess() {
        MedicalRecord record = new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1"), List.of("allergy1"));
        dataBean.getMedicalrecords().add(record);

        MedicalRecord updated = new MedicalRecord("John", "Doe", "02/02/1991", List.of("med2"), List.of("allergy2"));
        boolean updatedResult = medicalRecordService.updateMedicalRecord("John", "Doe", updated);

        Assertions.assertTrue(updatedResult);
        MedicalRecord r = dataBean.getMedicalrecords().get(0);
        Assertions.assertEquals("02/02/1991", r.getBirthdate());
        Assertions.assertEquals(List.of("med2"), r.getMedications());
        Assertions.assertEquals(List.of("allergy2"), r.getAllergies());
    }

    @Test
    void testUpdateMedicalRecordNotFound() {
        MedicalRecord updated = new MedicalRecord("John", "Doe", "02/02/1991", List.of("med2"), List.of("allergy2"));
        boolean updatedResult = medicalRecordService.updateMedicalRecord("Jane", "Doe", updated);
        Assertions.assertFalse(updatedResult);
    }

    @Test
    void testDeleteMedicalRecordSuccess() {
        MedicalRecord record = new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1"), List.of("allergy1"));
        dataBean.getMedicalrecords().add(record);

        boolean deleted = medicalRecordService.deleteMedicalRecord("John", "Doe");
        Assertions.assertTrue(deleted);
        Assertions.assertTrue(dataBean.getMedicalrecords().isEmpty());
    }

    @Test
    void testDeleteMedicalRecordNotFound() {
        boolean deleted = medicalRecordService.deleteMedicalRecord("Jane", "Doe");
        Assertions.assertFalse(deleted);
    }
}
