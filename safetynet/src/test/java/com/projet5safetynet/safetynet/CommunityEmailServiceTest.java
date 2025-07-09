package com.projet5safetynet.safetynet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.projet5safetynet.safetynet.model.DataBean;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.service.CommunityEmailService;
import com.projet5safetynet.safetynet.service.DataService;

@ExtendWith(MockitoExtension.class)
public class CommunityEmailServiceTest {
	@Mock
    private DataService dataService;
	@Mock
    private DataBean dataBean;
    private CommunityEmailService communityEmailService;

    @BeforeEach
    public void setup() {
        communityEmailService = new CommunityEmailService();
        ReflectionTestUtils.setField(communityEmailService, "dataService", dataService);
        when(dataService.getDataBean()).thenReturn(dataBean);
    }

    @Test
    public void testGetEmailsByCity() {
        List<Person> persons = List.of(
            new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john.boyd@mail.com"),
            new Person("Jane", "Doe", "29 15th St", "Culver", "97451", "841-874-6513", "jane.doe@mail.com"),
            new Person("Bob", "Smith", "834 Binoc Ave", "OtherCity", "97451", "841-874-6514", "bob.smith@mail.com"),
            new Person("Ann", "Brown", "10 Main St", "Culver", "97451", "841-874-6515", "ann.brown@mail.com"),
            new Person("Ann", "Brown", "10 Main St", "Culver", "97451", "841-874-6515", "ann.brown@mail.com") // duplicate email
        );

        when(dataBean.getPersons()).thenReturn(persons);

        List<String> emails = communityEmailService.getEmailsByCity("Culver");

        assertNotNull(emails);
        assertEquals(3, emails.size());
        assertTrue(emails.contains("john.boyd@mail.com"));
        assertTrue(emails.contains("jane.doe@mail.com"));
        assertTrue(emails.contains("ann.brown@mail.com"));
        assertFalse(emails.contains("bob.smith@mail.com"));
    }
}
