package com.aseds.tournamentmicroservice.mapper.impl;


import com.aseds.tournamentmicroservice.mapper.Mapper;
import com.aseds.tournamentmicroservice.model.Tournament;
import com.aseds.tournamentmicroservice.model.dto.TournamentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TournamentMapper implements Mapper<Tournament, TournamentDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public TournamentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Tournament mapTo(TournamentDTO tournamentDTO) {
        return modelMapper.map(tournamentDTO,Tournament.class);
    }

    @Override
    public TournamentDTO mapFrom(Tournament tournament) {
        return modelMapper.map(tournament,TournamentDTO.class);
    }
}
