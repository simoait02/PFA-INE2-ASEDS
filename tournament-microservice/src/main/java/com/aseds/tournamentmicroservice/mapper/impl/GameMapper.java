package com.aseds.tournamentmicroservice.mapper.impl;


import com.aseds.tournamentmicroservice.mapper.Mapper;
import com.aseds.tournamentmicroservice.model.Game;
import com.aseds.tournamentmicroservice.model.dto.GameDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GameMapper implements Mapper<Game, GameDTO> {
    private final ModelMapper modelMapper;

    public GameMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Game mapTo(GameDTO gameDTO) {
        return modelMapper.map(gameDTO,Game.class);
    }

    @Override
    public GameDTO mapFrom(Game game) {
        return modelMapper.map(game,GameDTO.class);
    }
}
