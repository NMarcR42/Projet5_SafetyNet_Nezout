package com.projet5safetynet.safetynet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.projet5safetynet.safetynet.dto.FloodResidentDTO;
import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.Firestation;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.service.DataService;
import com.projet5safetynet.safetynet.service.FloodService;

@ExtendWith(MockitoExtension.class)
public class FloodServiceTest {
	@Mock
    private DataService dataService;

    @Mock
    private DataBean dataBean;

    private FloodService floodService;

    @BeforeEach
    public void setup() {
        floodService = new FloodService();
        ReflectionTestUtils.setField(floodService, "dataService", dataService);

        when(dataService.getDataBean()).thenReturn(dataBean);
    }

    @Test
    public void testGetResidentsByStations() {
        List<Firestation> firestations = List.of(
            new Firestation("1509 Culver St", "1"),
            new Firestation("29 15th St", "2"),
            new Firestation("834 Binoc Ave", "1")
        );

        List<Person> persons = List.of(
            new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john.boyd@mail.com"),
            new Person("Jane", "Doe", "29 15th St", "Culver", "97451", "841-874-6513", "jane.doe@mail.com"),
            new Person("Bob", "Smith", "834 Binoc Ave", "Culver", "97451", "841-874-6514", "bob.smith@mail.com")
        );

        List<MedicalRecord> records = List.of(
            new MedicalRecord("John", "Boyd", "03/06/1984", List.of("med1:5mg"), List.of("pollen")),
            new MedicalRecord("Jane", "Doe", "04/18/2010", List.of("med2:10mg"), List.of()),
            new MedicalRecord("Bob", "Smith", "01/01/2000", List.of(), List.of("nuts"))
        );

        when(dataBean.getFirestations()).thenReturn(firestations);
        when(dataBean.getPersons()).thenReturn(persons);
        when(dataBean.getMedicalrecords()).thenReturn(records);

        Map<String, List<FloodResidentDTO>> result = floodService.getResidentsByStations(List.of("1"));

        assertNotNull(result);
        assertTrue(result.containsKey("1509 Culver St"));
        assertTrue(result.containsKey("834 Binoc Ave"));
        assertFalse(result.containsKey("29 15th St")); // station 2 pas demandé

        List<FloodResidentDTO> residentsCulver = result.get("1509 Culver St");
        assertEquals(1, residentsCulver.size());
        FloodResidentDTO john = residentsCulver.get(0);
        assertEquals("John", john.getFirstName());
        assertEquals("Boyd", john.getLastName());
        assertEquals("841-874-6512", john.getPhone());
        assertEquals(List.of("med1:5mg"), john.getMedications());
        assertEquals(List.of("pollen"), john.getAllergies());

        // Vérifie âge approximatif (de 1984)
        assertTrue(john.getAge() > 35 && john.getAge() < 45);
    }
}
