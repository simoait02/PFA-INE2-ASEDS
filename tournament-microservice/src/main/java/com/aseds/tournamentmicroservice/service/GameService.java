package com.aseds.tournamentmicroservice.service;


import com.aseds.tournamentmicroservice.mapper.impl.GameMapper;
import com.aseds.tournamentmicroservice.model.dto.GameDTO;
import com.aseds.tournamentmicroservice.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private static final String GAME_NOT_FOUND_MESSAGE = "Game with the id %d is not found";
    private static final String NULL_GAME_MESSAGE = "Game data cannot be null";
    private static final String MISSING_NAME_MESSAGE = "Game name is required";
    private static final String MISSING_GENRE_MESSAGE = "Game genre is required";
    private static final String GAME_DELETED_MESSAGE = "GAME DELETED";

    private final GameRepository repository;
    private final GameMapper gameMapper;

    @Autowired
    public GameService(GameRepository repository, GameMapper gameMapper) {
        this.repository = repository;
        this.gameMapper = gameMapper;
    }

    public GameDTO createGame(GameDTO gameDTO) {
        validateGameData(gameDTO);
        return gameMapper.mapFrom(repository.save(gameMapper.mapTo(gameDTO)));
    }

    public GameDTO getGameById(Long id) {
        return gameMapper.mapFrom(repository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException(String.format(GAME_NOT_FOUND_MESSAGE, id))
                )
        );
    }

    public String deleteGame(Long id) {
        repository.deleteById(id);
        return GAME_DELETED_MESSAGE;
    }

    private void validateGameData(GameDTO gameDTO) {
        if (gameDTO == null) {
            throw new IllegalArgumentException(NULL_GAME_MESSAGE);
        }

        if (gameDTO.getName() == null || gameDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException(MISSING_NAME_MESSAGE);
        }

        if (gameDTO.getGenre() == null || gameDTO.getGenre().trim().isEmpty()) {
            throw new IllegalArgumentException(MISSING_GENRE_MESSAGE);
        }
    }
}