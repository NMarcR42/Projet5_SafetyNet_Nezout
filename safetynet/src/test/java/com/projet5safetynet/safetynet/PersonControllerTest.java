package com.projet5safetynet.safetynet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.projet5safetynet.safetynet.controller.PersonController;
import com.projet5safetynet.safetynet.service.PersonService;
/*
//Mecanisme déclencheur des controllers concernés par les tests
@WebMvcTest(controllers = PersonController.class)
*/
public class PersonControllerTest {
	/*
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    //annotation obligatoire car la methode du controller est utilisé ici.
    private PersonService personService;

    @Test
    public void testGetPersons() throws Exception {
        mockMvc.perform(get("/persons")) //mockMVC permet d'appeller "perform" ce qui lance la requête
            .andExpect(status().isOk());
    }*/
}
