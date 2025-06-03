package com.aseds.tournamentmicroservice.mapper.impl;


import com.aseds.tournamentmicroservice.mapper.Mapper;
import com.aseds.tournamentmicroservice.model.Registration;
import com.aseds.tournamentmicroservice.model.dto.RegistrationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper implements Mapper<RegistrationDTO, Registration> {
    private final ModelMapper modelMapper;

    @Autowired
    public RegistrationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RegistrationDTO mapTo(Registration registration) {
        return modelMapper.map(registration,RegistrationDTO.class);
    }

    @Override
    public Registration mapFrom(RegistrationDTO registrationDTO) {
        return modelMapper.map(registrationDTO,Registration.class);
    }
}
