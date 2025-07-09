package com.projet5safetynet.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projet5safetynet.safetynet.service.CommunityEmailService;

@RestController
public class CommunityEmailController {
	@Autowired
    private CommunityEmailService communityEmailService;

    @GetMapping("/communityEmail")
    public List<String> getEmailsByCity(@RequestParam String city) {
        return communityEmailService.getEmailsByCity(city);
    }
}
