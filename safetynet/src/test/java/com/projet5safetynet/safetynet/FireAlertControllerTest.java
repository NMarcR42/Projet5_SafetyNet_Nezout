package com.projet5safetynet.safetynet;

import com.projet5safetynet.safetynet.controller.FireAlertController;
import com.projet5safetynet.safetynet.dto.FireResidentDTO;
import com.projet5safetynet.safetynet.service.FireAlertService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.projet5safetynet.safetynet.dto.FireResidentDTO;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FireAlertController.class)
public class FireAlertControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireAlertService fireAlertService;

    @Test
    public void testGetResidentsByAddress() throws Exception {
    	FireResidentDTO resident1 = new FireResidentDTO(
    	        "John",
    	        "Doe",
    	        30,
    	        "123-456-7890",
    	        List.of("med1", "med2"),
    	        List.of("allergy1")
    	    );

    	    FireResidentDTO resident2 = new FireResidentDTO(
    	        "Jane",
    	        "Smith",
    	        25,
    	        "098-765-4321",
    	        List.of(),
    	        List.of()
    	    );


        Mockito.when(fireAlertService.getResidentsByAddress(eq("1509 Culver St")))
                .thenReturn(List.of(resident1, resident2));

        mockMvc.perform(get("/fire")
                .param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
