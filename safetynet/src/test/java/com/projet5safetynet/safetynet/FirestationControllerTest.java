package com.projet5safetynet.safetynet;

import com.projet5safetynet.safetynet.controller.FirestationController;
import com.projet5safetynet.safetynet.dto.FirestationCoverageDTO;
import com.projet5safetynet.safetynet.model.Firestation;
import com.projet5safetynet.safetynet.service.FirestationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FirestationController.class)
public class FirestationControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationService firestationService;

    private Firestation firestationExample;
    private FirestationCoverageDTO coverageDTO;

    @BeforeEach
    public void setup() {
        firestationExample = new Firestation();
        firestationExample.setAddress("1509 Culver St");
        firestationExample.setStation("3");

        coverageDTO = new FirestationCoverageDTO();
        coverageDTO.setAdultCount(3);
        coverageDTO.setChildCount(1);
        coverageDTO.setPersons(List.of()); 
    }

    @Test
    public void testGetAllFirestations() throws Exception {
        Mockito.when(firestationService.getAllFirestations())
                .thenReturn(List.of(firestationExample));

        mockMvc.perform(get("/firestation/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].address", is("1509 Culver St")))
                .andExpect(jsonPath("$[0].station", is("3")));
    }

    @Test
    public void testGetCoverage() throws Exception {
        Mockito.when(firestationService.getPersonsByStation("3"))
                .thenReturn(coverageDTO);

        mockMvc.perform(get("/firestation")
                .param("stationNumber", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adultCount", is(3)))
                .andExpect(jsonPath("$.childCount", is(1)));
    }

    @Test
    public void testAddFirestationMapping() throws Exception {        
        String jsonFirestation = "{\"address\":\"1509 Culver St\", \"station\":\"3\"}";

        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation))
                .andExpect(status().isCreated())
                .andExpect(content().string("Mapping added"));
    }

    @Test
    public void testUpdateFirestationMapping_Success() throws Exception {
        Mockito.when(firestationService.updateFirestation(eq("1509 Culver St"), eq("3")))
                .thenReturn(true);

        String jsonFirestation = "{\"address\":\"1509 Culver St\", \"station\":\"3\"}";

        mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation))
                .andExpect(status().isOk())
                .andExpect(content().string("Mapping updated"));
    }

    @Test
    public void testUpdateFirestationMapping_NotFound() throws Exception {
        Mockito.when(firestationService.updateFirestation(eq("1509 Culver St"), eq("3")))
                .thenReturn(false);

        String jsonFirestation = "{\"address\":\"1509 Culver St\", \"station\":\"3\"}";

        mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Mapping not found"));
    }

    @Test
    public void testDeleteFirestationMapping_Success() throws Exception {
        Mockito.when(firestationService.deleteFirestation("1509 Culver St", "3"))
                .thenReturn(true);

        mockMvc.perform(delete("/firestation")
                .param("address", "1509 Culver St")
                .param("stationNumber", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mapping deleted"));
    }

    @Test
    public void testDeleteFirestationMapping_NotFound() throws Exception {
        Mockito.when(firestationService.deleteFirestation("1509 Culver St", "3"))
                .thenReturn(false);

        mockMvc.perform(delete("/firestation")
                .param("address", "1509 Culver St")
                .param("stationNumber", "3"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Mapping not found"));
    }
}
