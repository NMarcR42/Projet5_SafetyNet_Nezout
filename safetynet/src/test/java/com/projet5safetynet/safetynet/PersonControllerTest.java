package com.projet5safetynet.safetynet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet5safetynet.safetynet.controller.PersonController;
import com.projet5safetynet.safetynet.model.Person;
import com.projet5safetynet.safetynet.service.PersonService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPersons() throws Exception {
        List<Person> persons = List.of(
            new Person("John", "Doe", "1509 Culver St", "Culver", "97451", "841-874-6512", "johndoe@example.com"),
            new Person("Jane", "Smith", "29 15th St", "Culver", "97451", "841-874-8888", "janesmith@example.com")
        );

        when(personService.getAllPersons()).thenReturn(persons);

        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    public void testAddPerson() throws Exception {
        Person newPerson = new Person("Alice", "Brown", "10 Main St", "Culver", "97451", "841-111-2222", "alice@example.com");

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPerson)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Person added"));
    }

    @Test
    public void testUpdatePersonFound() throws Exception {
        Person updatedPerson = new Person("Alice", "Brown", "10 Main St", "Culver", "97451", "841-111-2222", "alice@example.com");

        when(personService.updatePerson("Alice", "Brown", updatedPerson)).thenReturn(true);

        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPerson)))
                .andExpect(status().isOk())
                .andExpect(content().string("Person updated"));
    }

    @Test
    public void testUpdatePersonNotFound() throws Exception {
        Person updatedPerson = new Person("Bob", "NotExist", "Somewhere", "City", "00000", "000-000-0000", "bob@none.com");

        when(personService.updatePerson("Bob", "NotExist", updatedPerson)).thenReturn(false);

        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPerson)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Person not found"));
    }

    @Test
    public void testDeletePersonFound() throws Exception {
        when(personService.deletePerson("Alice", "Brown")).thenReturn(true);

        mockMvc.perform(delete("/person")
                .param("firstName", "Alice")
                .param("lastName", "Brown"))
                .andExpect(status().isOk())
                .andExpect(content().string("Person deleted"));
    }

    @Test
    public void testDeletePersonNotFound() throws Exception {
        when(personService.deletePerson("Bob", "NotExist")).thenReturn(false);

        mockMvc.perform(delete("/person")
                .param("firstName", "Bob")
                .param("lastName", "NotExist"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Person not found"));
    }
}
