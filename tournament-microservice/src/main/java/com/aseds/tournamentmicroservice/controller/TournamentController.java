package com.aseds.tournamentmicroservice.controller;


import com.aseds.tournamentmicroservice.model.Tournament;
import com.aseds.tournamentmicroservice.model.dto.TournamentDTO;
import com.aseds.tournamentmicroservice.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }

    @PostMapping
    public ResponseEntity<String> createTournament(@RequestBody TournamentDTO tournamentDTO) {
        String tournamentId = tournamentService.createTournament(tournamentDTO);
        return ResponseEntity.ok("Tournament created with ID: " + tournamentId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournamentById(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentService.getTournamentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTournament(@PathVariable Long id, @RequestBody TournamentDTO tournamentDTO) {
        tournamentService.updateTournament(id, tournamentDTO);
        return ResponseEntity.ok("Tournament updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTournament(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentService.deleteTournament(id));
    }
}
