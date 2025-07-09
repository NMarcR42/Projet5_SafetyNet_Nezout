package com.projet5safetynet.safetynet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projet5safetynet.safetynet.dto.FirestationCoverageDTO;
import com.projet5safetynet.safetynet.dto.PersonCoverageInfo;
import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.Firestation;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.service.DataService;
import com.projet5safetynet.safetynet.service.FirestationService;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {
	@Mock
    private DataService dataService;

    @InjectMocks
    private FirestationService firestationService;

    private DataBean dataBean;

    @BeforeEach
    void setup() {
        dataBean = new DataBean();
        dataBean.setFirestations(new ArrayList<>());
        dataBean.setPersons(new ArrayList<>());
        dataBean.setMedicalrecords(new ArrayList<>());
        when(dataService.getDataBean()).thenReturn(dataBean);
    }

    @Test
    void testAddAndGetAllFirestations() {
        Firestation f = new Firestation("123 Street", "1");
        firestationService.addFirestation(f);

        List<Firestation> firestations = firestationService.getAllFirestations();
        Assertions.assertEquals(1, firestations.size());
        Assertions.assertEquals("1", firestations.get(0).getStation());
    }

    @Test
    void testUpdateFirestationSuccess() {
        Firestation f = new Firestation("123 Street", "1");
        dataBean.getFirestations().add(f);

        boolean updated = firestationService.updateFirestation("123 Street", "2");
        Assertions.assertTrue(updated);
        Assertions.assertEquals("2", dataBean.getFirestations().get(0).getStation());
    }

    @Test
    void testUpdateFirestationNotFound() {
        boolean updated = firestationService.updateFirestation("Non Existent Address", "2");
        Assertions.assertFalse(updated);
    }

    @Test
    void testDeleteFirestationByAddress() {
        Firestation f = new Firestation("123 Street", "1");
        dataBean.getFirestations().add(f);

        boolean deleted = firestationService.deleteFirestation("123 Street", null);
        Assertions.assertTrue(deleted);
        Assertions.assertTrue(dataBean.getFirestations().isEmpty());
    }

    @Test
    void testDeleteFirestationByStation() {
        Firestation f = new Firestation("123 Street", "1");
        dataBean.getFirestations().add(f);

        boolean deleted = firestationService.deleteFirestation(null, "1");
        Assertions.assertTrue(deleted);
        Assertions.assertTrue(dataBean.getFirestations().isEmpty());
    }

    @Test
    void testDeleteFirestationNotFound() {
        boolean deleted = firestationService.deleteFirestation("Non Existent Address", "9");
        Assertions.assertFalse(deleted);
    }

    @Test
    void testGetPersonsByStationCounts() {
        // Setup firestation and persons
        Firestation f = new Firestation("123 Street", "1");
        dataBean.getFirestations().add(f);

        Person childPerson = new Person("Kid", "Young", "123 Street", "City", "12345", "111222333", "kid@example.com");
        Person adultPerson = new Person("Adult", "Old", "123 Street", "City", "12345", "444555666", "adult@example.com");
        dataBean.getPersons().addAll(List.of(childPerson, adultPerson));

        // MedicalRecords with birthdates - child under 18, adult over 18
        MedicalRecord childRecord = new MedicalRecord("Kid", "Young", "01/01/2010", List.of(), List.of());
        MedicalRecord adultRecord = new MedicalRecord("Adult", "Old", "01/01/1980", List.of(), List.of());
        dataBean.getMedicalrecords().addAll(List.of(childRecord, adultRecord));

        FirestationCoverageDTO dto = firestationService.getPersonsByStation("1");

        Assertions.assertEquals(2, dto.getPersons().size());
        Assertions.assertEquals(1, dto.getChildCount());
        Assertions.assertEquals(1, dto.getAdultCount());
    }
}
