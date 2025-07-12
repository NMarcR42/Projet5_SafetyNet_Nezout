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
import com.projet5safetynet.safetynet.controller.CommunityEmailController;
import com.projet5safetynet.safetynet.service.CommunityEmailService;

@WebMvcTest(CommunityEmailController.class)
public class CommunityEmailControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommunityEmailService communityEmailService;

    @Test
    public void testGetEmailsByCity() throws Exception {
        List<String> mockEmails = List.of("john.boyd@mail.com", "jane.doe@mail.com");

        when(communityEmailService.getEmailsByCity("Culver")).thenReturn(mockEmails);

        mockMvc.perform(get("/communityEmail")
                .param("city", "Culver"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]").value("john.boyd@mail.com"))
                .andExpect(jsonPath("$[1]").value("jane.doe@mail.com"));

        verify(communityEmailService, times(1)).getEmailsByCity("Culver");
    }
}
