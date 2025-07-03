package com.projet5safetynet.safetynet.controller;
import com.projet5safetynet.safetynet.dto.ChildAlertDTO;
import com.projet5safetynet.safetynet.service.ChildAlertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/childAlert")
public class ChildAlertController {
	private static final Logger logger = LogManager.getLogger(ChildAlertController.class);

    @Autowired
    private ChildAlertService childAlertService;

    @GetMapping
    public List<ChildAlertDTO> getChildrenByAddress(@RequestParam String address) {
        logger.info("Requête reçue GET /childAlert?address={}", address);
        List<ChildAlertDTO> response = childAlertService.getChildrenByAddress(address);
        logger.info("Réponse : {} enfant(s) trouvé(s)", response.size());
        return response;
    }
}
