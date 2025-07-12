package com.projet5safetynet.safetynet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.projet5safetynet.safetynet.controller.ChildAlertController;
import com.projet5safetynet.safetynet.dto.ChildAlertDTO;
import com.projet5safetynet.safetynet.service.ChildAlertService;

@WebMvcTest(ChildAlertController.class)
public class ChildAlertControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChildAlertService childAlertService;

    @Test
    public void testGetChildrenByAddress() throws Exception {
        List<ChildAlertDTO> mockResponse = List.of(new ChildAlertDTO("John", "Doe", 10, List.of()));

        when(childAlertService.getChildrenByAddress("1509 3rd St")).thenReturn(mockResponse);

        mockMvc.perform(get("/childAlert")
                .param("address", "1509 3rd St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"));

        verify(childAlertService, times(1)).getChildrenByAddress("1509 3rd St");
    }
}
