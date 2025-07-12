package com.projet5safetynet.safetynet.controller;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projet5safetynet.safetynet.service.CommunityEmailService;

@RestController
public class CommunityEmailController {
	private static final Logger logger = LogManager.getLogger(CommunityEmailController.class);

    @Autowired
    private CommunityEmailService communityEmailService;

    @GetMapping("/communityEmail")
    public List<String> getEmailsByCity(@RequestParam String city) {
        logger.info("Requête reçue GET /communityEmail?city={}", city);
        List<String> emails = communityEmailService.getEmailsByCity(city);
        logger.info("Réponse : {} email(s) trouvé(s)", emails.size());
        return emails;
    }
}
