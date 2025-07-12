package com.projet5safetynet.safetynet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.projet5safetynet.safetynet.controller.FloodStationController;
import com.projet5safetynet.safetynet.dto.FloodResidentDTO;
import com.projet5safetynet.safetynet.service.FloodService;

@WebMvcTest(FloodStationController.class)
public class FloodStationControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private FloodService floodService;

    @Test
    public void testGetResidentsByStations() throws Exception {
    	// Création de résidents mock
        FloodResidentDTO resident1 = new FloodResidentDTO(
            "John", "Doe", 35, "123-456-7890",
            List.of("med1", "med2"),
            List.of("pollen")
        );

        FloodResidentDTO resident2 = new FloodResidentDTO(
            "Jane", "Smith", 28, "987-654-3210",
            List.of(),
            List.of("peanut")
        );

        // Réponse mock du service
        Map<String, List<FloodResidentDTO>> mockResponse = Map.of(
            "1", List.of(resident1),
            "2", List.of(resident2)
        );

        Mockito.when(floodService.getResidentsByStations(Mockito.anyList()))
               .thenReturn(mockResponse);

        // Requête HTTP GET simulée
        mockMvc.perform(get("/flood/stations")
                .param("stations", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['1']", hasSize(1)))
                .andExpect(jsonPath("$.['2']", hasSize(1)))
                .andExpect(jsonPath("$.['1'][0].firstName").value("John"))
                .andExpect(jsonPath("$.['2'][0].firstName").value("Jane"));
    }
}
