package com.projet5safetynet.safetynet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet5safetynet.safetynet.controller.PersonInfoLastNameController;
import com.projet5safetynet.safetynet.dto.PersonInfoLastNameDTO;
import com.projet5safetynet.safetynet.service.PersonInfoLastNameService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@WebMvcTest(PersonInfoLastNameController.class)
public class PersonInfoLastNameControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonInfoLastNameService personInfoService;

    @Test
    public void testGetPersonsByLastName() throws Exception {
        List<PersonInfoLastNameDTO> result = List.of(
            new PersonInfoLastNameDTO("John", "Doe", "123 Street", 35, "john@example.com", List.of("med1"), List.of("allergy1")),
            new PersonInfoLastNameDTO("Jane", "Doe", "123 Street", 33, "jane@example.com", List.of(), List.of("allergy2"))
        );

        when(personInfoService.getPersonsByLastName("Doe")).thenReturn(result);

        mockMvc.perform(get("/personInfolastName").param("lastName", "Doe"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].firstName").value("John"))
            .andExpect(jsonPath("$[0].age").value(35))
            .andExpect(jsonPath("$[1].firstName").value("Jane"))
            .andExpect(jsonPath("$[1].email").value("jane@example.com"));
    }
}
