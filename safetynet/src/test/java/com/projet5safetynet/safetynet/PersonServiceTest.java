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
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.service.DataService;
import com.projet5safetynet.safetynet.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
	@Mock
    private DataService dataService;

    @InjectMocks
    private PersonService personService;

    private DataBean dataBean;

    @BeforeEach
    void setup() {
        dataBean = new DataBean();
        dataBean.setPersons(new ArrayList<>());
        when(dataService.getDataBean()).thenReturn(dataBean);
    }

    @Test
    void testAddAndGetAllPersons() {
        Person person = new Person("John", "Doe", "123 Street", "City", "12345", "1234567890", "john@example.com");
        personService.addPerson(person);

        List<Person> persons = personService.getAllPersons();
        Assertions.assertEquals(1, persons.size());
        Assertions.assertEquals("John", persons.get(0).getFirstName());
    }

    @Test
    void testUpdatePersonSuccess() {
        Person person = new Person("John", "Doe", "123 Street", "City", "12345", "1234567890", "john@example.com");
        dataBean.getPersons().add(person);

        Person updated = new Person("John", "Doe", "456 Avenue", "New City", "67890", "0987654321", "john@newmail.com");
        boolean updatedResult = personService.updatePerson("John", "Doe", updated);

        Assertions.assertTrue(updatedResult);
        Person p = dataBean.getPersons().get(0);
        Assertions.assertEquals("456 Avenue", p.getAddress());
        Assertions.assertEquals("New City", p.getCity());
        Assertions.assertEquals("67890", p.getZip());
        Assertions.assertEquals("0987654321", p.getPhone());
        Assertions.assertEquals("john@newmail.com", p.getEmail());
    }

    @Test
    void testUpdatePersonNotFound() {
        Person updated = new Person("John", "Doe", "456 Avenue", "New City", "67890", "0987654321", "john@newmail.com");
        boolean updatedResult = personService.updatePerson("Jane", "Doe", updated);
        Assertions.assertFalse(updatedResult);
    }

    @Test
    void testDeletePersonSuccess() {
        Person person = new Person("John", "Doe", "123 Street", "City", "12345", "1234567890", "john@example.com");
        dataBean.getPersons().add(person);

        boolean deleted = personService.deletePerson("John", "Doe");
        Assertions.assertTrue(deleted);
        Assertions.assertTrue(dataBean.getPersons().isEmpty());
    }

    @Test
    void testDeletePersonNotFound() {
        boolean deleted = personService.deletePerson("Jane", "Doe");
        Assertions.assertFalse(deleted);
    }
}
