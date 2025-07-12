package com.projet5safetynet.safetynet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.projet5safetynet.safetynet.controller.MedicalRecordController;
import com.projet5safetynet.safetynet.model.MedicalRecord;
import com.projet5safetynet.safetynet.service.MedicalRecordService;


@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void testGetAllMedicalRecords() throws Exception {
        List<MedicalRecord> records = List.of(
            new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1"), List.of("allergy1")),
            new MedicalRecord("Jane", "Smith", "02/02/1980", List.of("med2"), List.of("allergy2"))
        );

        Mockito.when(medicalRecordService.getAllMedicalRecords()).thenReturn(records);

        mockMvc.perform(get("/medicalrecord"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].firstName").value("John"))
            .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }
    

    @Test
    public void testAddMedicalRecord() throws Exception {
    	MedicalRecord record = new MedicalRecord(
    	        "Alice",
    	        "Wonderland",
    	        "10/10/1995",
    	        List.of("medX", "medY"),
    	        List.of("allergyA", "allergyB")
    	    );

    	    mockMvc.perform(post("/medicalrecord")
    	            .contentType(MediaType.APPLICATION_JSON)
    	            .content(asJsonString(record)))
    	            .andExpect(status().isCreated())
    	            .andExpect(content().string("Medical record added"));
    }

    // Méthode utilitaire pour convertir un objet en JSON string (Jackson)
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
