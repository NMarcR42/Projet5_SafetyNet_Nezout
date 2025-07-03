package com.projet5safetynet.safetynet.controller;

import com.projet5safetynet.safetynet.dto.FireResidentDTO;
import com.projet5safetynet.safetynet.service.FireAlertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fire")
public class FireAlertController {

    private static final Logger logger = LogManager.getLogger(FireAlertController.class);

    @Autowired
    private FireAlertService fireAlertService;

    @GetMapping
    public List<FireResidentDTO> getResidentsByAddress(@RequestParam String address) {
        logger.info("Requête reçue GET /fire?address={}", address);
        List<FireResidentDTO> response = fireAlertService.getResidentsByAddress(address);
        logger.info("Réponse : {} résident(s) trouvé(s)", response.size());
        return response;
    }
}
