package com.aseds.tournamentmicroservice.service;


import com.aseds.tournamentmicroservice.mapper.impl.GameMapper;
import com.aseds.tournamentmicroservice.mapper.impl.TournamentMapper;
import com.aseds.tournamentmicroservice.model.Game;
import com.aseds.tournamentmicroservice.model.Tournament;
import com.aseds.tournamentmicroservice.model.dto.TournamentDTO;
import com.aseds.tournamentmicroservice.repository.GameRepository;
import com.aseds.tournamentmicroservice.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {
    private static final String TOURNAMENT_NOT_FOUND_MESSAGE = "Tournament with ID %d not found";
    private static final String NULL_TOURNAMENT_MESSAGE = "Tournament data cannot be null";
    private static final String MISSING_TITLE_MESSAGE = "Tournament title is required";
    private static final String MISSING_DATE_MESSAGE = "Tournament date is required";
    private static final String TOURNAMENT_DELETED_MESSAGE = "Tournament deleted successfully";
    private static final String GAME_NOT_FOUND_MESSAGE = "Game with ID %d not found";

    private final TournamentRepository tournamentRepository;
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final TournamentMapper tournamentMapper;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository, GameRepository gameRepository, GameMapper gameMapper, TournamentMapper tournamentMapper) {
        this.tournamentRepository = tournamentRepository;
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.tournamentMapper = tournamentMapper;
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public String createTournament(TournamentDTO tournamentDTO) {
        validateTournamentData(tournamentDTO);

        List<Game> managedGames = tournamentDTO.getGames().stream()
                .map(g -> gameRepository.findById(g.getId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                String.format(GAME_NOT_FOUND_MESSAGE, g.getId())))
                )
                .toList();

        tournamentDTO.setGames(managedGames);
        Tournament created = tournamentRepository.save(
                tournamentMapper.mapTo(tournamentDTO)
        );
        return created.getId().toString();
    }

    public TournamentDTO getTournamentById(Long id) {
        return tournamentMapper.mapFrom( tournamentRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException(
                        String.format(TOURNAMENT_NOT_FOUND_MESSAGE, id))
                )
        );
    }

    public void updateTournament(Long id, TournamentDTO updatedTournament) {
        validateTournamentData(updatedTournament);

        TournamentDTO existingTournament = getTournamentById(id);
        TournamentDTO updated = updateTournamentFields(existingTournament, updatedTournament);
        tournamentRepository.save(tournamentMapper.mapTo(updated));
    }

    public String deleteTournament(Long id) {
        getTournamentById(id);
        tournamentRepository.deleteById(id);
        return TOURNAMENT_DELETED_MESSAGE;
    }

    private TournamentDTO updateTournamentFields(TournamentDTO existingTournament, TournamentDTO updatedTournament) {
        Optional.ofNullable(updatedTournament.getTitle())
                .filter(title -> !title.equals(existingTournament.getTitle()))
                .ifPresent(existingTournament::setTitle);

        Optional.ofNullable(updatedTournament.getDate())
                .filter(date -> !date.equals(existingTournament.getDate()))
                .ifPresent(existingTournament::setDate);

        Optional.ofNullable(updatedTournament.getLocation())
                .filter(location -> !location.equals(existingTournament.getLocation()))
                .ifPresent(existingTournament::setLocation);

        Optional.ofNullable(updatedTournament.getDescription())
                .filter(description -> !description.equals(existingTournament.getDescription()))
                .ifPresent(existingTournament::setDescription);

        Optional.ofNullable(updatedTournament.getGames())
                .ifPresent(existingTournament::setGames);

        Optional.ofNullable(updatedTournament.getRegistrations())
                .ifPresent(existingTournament::setRegistrations);

        return existingTournament;
    }

    private void validateTournamentData(TournamentDTO tournamentDTO) {
        if (tournamentDTO == null) {
            throw new IllegalArgumentException(NULL_TOURNAMENT_MESSAGE);
        }
        if (tournamentDTO.getTitle() == null || tournamentDTO.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException(MISSING_TITLE_MESSAGE);
        }
        if (tournamentDTO.getDate() == null) {
            throw new IllegalArgumentException(MISSING_DATE_MESSAGE);
        }
    }
}