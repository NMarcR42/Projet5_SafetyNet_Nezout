package com.projet5safetynet.safetynet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projet5safetynet.safetynet.dto.FireResidentDTO;
import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.service.DataService;
import com.projet5safetynet.safetynet.service.FireAlertService;

@ExtendWith(MockitoExtension.class)
public class FireAlertServiceTest {
	@Mock
    private DataService dataService;

    @InjectMocks
    private FireAlertService fireAlertService;

    @Test
    public void testGetResidentsByAddress() {
        // Arrange : mock données
        Person person1 = new Person("John", "Doe", "123 Street", "City", "12345", "123-456", "email1@test.com");
        Person person2 = new Person("Jane", "Doe", "123 Street", "City", "12345", "789-012", "email2@test.com");

        MedicalRecord mr1 = new MedicalRecord("John", "Doe", "01/01/2000", List.of("med1"), List.of("allergy1"));
        MedicalRecord mr2 = new MedicalRecord("Jane", "Doe", "01/01/2010", List.of(), List.of());

        DataBean dataBean = Mockito.mock(DataBean.class);
        Mockito.when(dataBean.getPersons()).thenReturn(List.of(person1, person2));
        Mockito.when(dataBean.getMedicalrecords()).thenReturn(List.of(mr1, mr2));
        Mockito.when(dataService.getDataBean()).thenReturn(dataBean);

        // Act
        List<FireResidentDTO> residents = fireAlertService.getResidentsByAddress("123 Street");

        // Assert
        assertEquals(2, residents.size());

        FireResidentDTO resident1 = residents.get(0);
        assertEquals("John", resident1.getFirstName());
        assertEquals(25, resident1.getAge(), "Age devrait être calculé (approx)"); // selon la date actuelle
        assertEquals("123-456", resident1.getPhone());
        assertTrue(resident1.getMedications().contains("med1"));
        assertTrue(resident1.getAllergies().contains("allergy1"));

        FireResidentDTO resident2 = residents.get(1);
        assertEquals("Jane", resident2.getFirstName());
        assertEquals("789-012", resident2.getPhone());
        assertTrue(resident2.getMedications().isEmpty());
    }
}
