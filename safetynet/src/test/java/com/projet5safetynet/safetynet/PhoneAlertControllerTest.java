package com.projet5safetynet.safetynet;

import com.projet5safetynet.safetynet.controller.PhoneAlertController;
import com.projet5safetynet.safetynet.dto.PhoneAlertDTO;
import com.projet5safetynet.safetynet.service.PhoneAlertService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PhoneAlertController.class)
public class PhoneAlertControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneAlertService phoneAlertService;

    @Test
    public void testGetPhones() throws Exception {
        PhoneAlertDTO dto1 = new PhoneAlertDTO("123-456-7890");
        PhoneAlertDTO dto2 = new PhoneAlertDTO("987-654-3210");

        Mockito.when(phoneAlertService.getPhoneNumbersByStation(eq("3")))
                .thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/phoneAlert")
                .param("firestation", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].phone").value("123-456-7890"))
                .andExpect(jsonPath("$[1].phone").value("987-654-3210"));
    }
}
