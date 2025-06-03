package com.aseds.tournamentmicroservice.controller;


import com.aseds.tournamentmicroservice.model.dto.RegistrationDTO;
import com.aseds.tournamentmicroservice.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<String> registerParticipant(@RequestBody RegistrationDTO registrationDTO) {
        String result = registrationService.registerParticipant(registrationDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelRegistration(@PathVariable Long id) {
        String result = registrationService.cancelRegistration(id);
        return ResponseEntity.ok(result);
    }
}
