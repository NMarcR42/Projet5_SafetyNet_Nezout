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

import com.projet5safetynet.safetynet.dto.PhoneAlertDTO;
import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.Firestation;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.service.DataService;
import com.projet5safetynet.safetynet.service.PhoneAlertService;

public class PhoneAlertServiceTest {
	@Mock
    private DataService dataService;

    @InjectMocks
    private PhoneAlertService phoneAlertService;

    private DataBean dataBean;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dataBean = new DataBean();

        when(dataService.getDataBean()).thenReturn(dataBean);
    }

    @Test
    public void testGetPhoneNumbersByStation_WithResults() {
        Firestation firestation1 = new Firestation("1509 3rd St", "1");
        Firestation firestation2 = new Firestation("29 15th St", "2");
        dataBean.setFirestations(Arrays.asList(firestation1, firestation2));

        Person person1 = new Person();
        person1.setAddress("1509 3rd St");
        person1.setPhone("841-874-6512");

        Person person2 = new Person();
        person2.setAddress("1509 3rd St");
        person2.setPhone("841-874-6513");

        Person person3 = new Person();
        person3.setAddress("29 15th St");
        person3.setPhone("841-874-6514");

        dataBean.setPersons(Arrays.asList(person1, person2, person3));

        List<PhoneAlertDTO> phones = phoneAlertService.getPhoneNumbersByStation("1");

        assertNotNull(phones);
        assertEquals(2, phones.size());

        List<String> phoneNumbers = Arrays.asList(phones.get(0).getPhone(), phones.get(1).getPhone());

        assertTrue(phoneNumbers.contains("841-874-6512"));
        assertTrue(phoneNumbers.contains("841-874-6513"));
    }

    @Test
    public void testGetPhoneNumbersByStation_NoResults() {
        dataBean.setFirestations(Collections.emptyList());
        dataBean.setPersons(Collections.emptyList());

        List<PhoneAlertDTO> phones = phoneAlertService.getPhoneNumbersByStation("1");

        assertNotNull(phones);
        assertTrue(phones.isEmpty());
    }
}
