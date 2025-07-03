package com.projet5safetynet.safetynet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.projet5safetynet.safetynet.service.PersonService;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class SafetynetApplication implements CommandLineRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(SafetynetApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
        System.out.println("Hello World!"); // Test console
    }
}
