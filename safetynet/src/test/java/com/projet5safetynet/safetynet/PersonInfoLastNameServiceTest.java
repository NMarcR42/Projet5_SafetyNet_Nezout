package com.projet5safetynet.safetynet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.projet5safetynet.safetynet.dto.PersonInfoLastNameDTO;
import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.service.DataService;
import com.projet5safetynet.safetynet.service.PersonInfoLastNameService;

@ExtendWith(MockitoExtension.class)
public class PersonInfoLastNameServiceTest {
	@Mock
    private DataService dataService;
	@Mock
    private DataBean dataBean;

    private PersonInfoLastNameService personInfoLastNameService;

    @BeforeEach
    public void setup() {
        personInfoLastNameService = new PersonInfoLastNameService();
        ReflectionTestUtils.setField(personInfoLastNameService, "dataService", dataService);
        when(dataService.getDataBean()).thenReturn(dataBean);
    }

    @Test
    public void testGetPersonsByLastName() {
        List<Person> persons = List.of(
            new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john.boyd@mail.com"),
            new Person("Jane", "Doe", "29 15th St", "Culver", "97451", "841-874-6513", "jane.doe@mail.com"),
            new Person("Johnny", "Boyd", "834 Binoc Ave", "Culver", "97451", "841-874-6514", "johnny.boyd@mail.com")
        );

        List<MedicalRecord> records = List.of(
            new MedicalRecord("John", "Boyd", "03/06/1984", List.of("med1:5mg"), List.of("pollen")),
            new MedicalRecord("Jane", "Doe", "04/18/2010", List.of("med2:10mg"), List.of()),
            new MedicalRecord("Johnny", "Boyd", "01/01/2000", List.of(), List.of("nuts"))
        );

        when(dataBean.getPersons()).thenReturn(persons);
        when(dataBean.getMedicalrecords()).thenReturn(records);

        List<PersonInfoLastNameDTO> result = personInfoLastNameService.getPersonsByLastName("Boyd");

        assertNotNull(result);
        assertEquals(2, result.size());

        PersonInfoLastNameDTO john = result.stream()
            .filter(p -> p.getFirstName().equals("John"))
            .findFirst().orElse(null);

        assertNotNull(john);
        assertEquals("1509 Culver St", john.getAddress());
        assertEquals("john.boyd@mail.com", john.getEmail());
        assertEquals(List.of("med1:5mg"), john.getMedications());
        assertEquals(List.of("pollen"), john.getAllergies());
        assertTrue(john.getAge() > 35 && john.getAge() < 45);
    }
}
