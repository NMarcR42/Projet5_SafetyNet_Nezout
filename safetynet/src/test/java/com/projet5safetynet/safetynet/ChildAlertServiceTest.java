package com.projet5safetynet.safetynet;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.projet5safetynet.safetynet.dto.ChildAlertDTO;
import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.service.ChildAlertService;
import com.projet5safetynet.safetynet.service.DataService;

public class ChildAlertServiceTest {
	@Mock
    private DataService dataService;

    @InjectMocks
    private ChildAlertService childAlertService;

    private DataBean dataBean;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Création d'un DataBean fictif
        dataBean = new DataBean();

        // Simuler dataService.getDataBean() pour retourner ce dataBean
        when(dataService.getDataBean()).thenReturn(dataBean);
    }

    @Test
    public void testGetChildrenByAddress_WithChildren() {
        // Données Person
        Person child = new Person("John", "Doe", "1509 3rd St", "Culver", "97451", "841-874-6512", "johndoe@email.com");
        Person parent = new Person("Jane", "Doe", "1509 3rd St", "Culver", "97451", "841-874-6513", "janedoe@email.com");

        dataBean.setPersons(Arrays.asList(child, parent));

        // Données MedicalRecord (child < 18 ans)
        MedicalRecord childRecord = new MedicalRecord("John", "Doe", "03/06/2010", Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));
        MedicalRecord parentRecord = new MedicalRecord("Jane", "Doe", "04/06/1980", Collections.emptyList(), Collections.emptyList());

        dataBean.setMedicalrecords(Arrays.asList(childRecord, parentRecord));

        List<ChildAlertDTO> result = childAlertService.getChildrenByAddress("1509 3rd St");

        assertNotNull(result);
        assertEquals(1, result.size());

        ChildAlertDTO childDTO = result.get(0);
        assertEquals("John", childDTO.getFirstName());
        assertEquals("Doe", childDTO.getLastName());
        assertTrue(childDTO.getAge() <= 18);

        // Vérifier qu'il y a 1 autre membre dans le foyer
        assertEquals(1, childDTO.getOtherHouseholdMembers().size());
        assertEquals("Jane", childDTO.getOtherHouseholdMembers().get(0).getFirstName());
    }

    @Test
    public void testGetChildrenByAddress_NoChildren() {
        // Seulement des adultes
        Person adult = new Person("Jane", "Doe", "1509 3rd St", "Culver", "97451", "841-874-6513", "janedoe@email.com");

        dataBean.setPersons(Collections.singletonList(adult));

        MedicalRecord adultRecord = new MedicalRecord("Jane", "Doe", "04/06/1980", Collections.emptyList(), Collections.emptyList());

        dataBean.setMedicalrecords(Collections.singletonList(adultRecord));

        List<ChildAlertDTO> result = childAlertService.getChildrenByAddress("1509 3rd St");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetChildrenByAddress_NoPersonsAtAddress() {
        dataBean.setPersons(Collections.emptyList());
        dataBean.setMedicalrecords(Collections.emptyList());

        List<ChildAlertDTO> result = childAlertService.getChildrenByAddress("Unknown address");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
