package com.projet5safetynet.safetynet.controller;

import com.projet5safetynet.safetynet.dto.PhoneAlertDTO;
import com.projet5safetynet.safetynet.service.PhoneAlertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertController {
	private static final Logger logger = LogManager.getLogger(PhoneAlertController.class);

    @Autowired
    private PhoneAlertService phoneAlertService;

    @GetMapping
    public List<PhoneAlertDTO> getPhones(@RequestParam String firestation) {
        logger.info("Requête reçue GET /phoneAlert?firestation={}", firestation);
        List<PhoneAlertDTO> phones = phoneAlertService.getPhoneNumbersByStation(firestation);
        logger.info("Réponse : {} numéro(s) trouvé(s)", phones.size());
        return phones;
    }
}
