package com.aseds.tournamentmicroservice.service;


import com.aseds.tournamentmicroservice.mapper.impl.RegistrationMapper;
import com.aseds.tournamentmicroservice.model.Registration;
import com.aseds.tournamentmicroservice.model.dto.RegistrationDTO;
import com.aseds.tournamentmicroservice.model.dto.TournamentDTO;
import com.aseds.tournamentmicroservice.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private static final String REGISTRATION_ADDED = "REGISTRATION ADDED";
    private static final String REGISTRATION_EXISTS = "REGISTRATION ALREADY EXISTS";
    private static final String REGISTRATION_DELETED = "REGISTRATION DELETED";
    private static final String REGISTRATION_NOT_FOUND = "REGISTRATION DOES NOT EXIST";

    private final RegistrationMapper registrationMapper;
    private final RegistrationRepository registrationRepository;
    private final TournamentService tournamentService;

    @Autowired
    public RegistrationService(RegistrationMapper registrationMapper,
                               RegistrationRepository registrationRepository, TournamentService tournamentService) {
        this.registrationMapper = registrationMapper;
        this.registrationRepository = registrationRepository;
        this.tournamentService = tournamentService;
    }

    public String registerParticipant(RegistrationDTO registrationDTO) {
        if (registrationExists(registrationDTO)) {
            return REGISTRATION_EXISTS;
        }

        Registration savedRegistration = registrationRepository.save(
                registrationMapper.mapFrom(registrationDTO)
        );


        return REGISTRATION_ADDED;
    }

    public String cancelRegistration(Long id) {
        if (!registrationRepository.existsById(id)) {
            return REGISTRATION_NOT_FOUND;
        }

        registrationRepository.deleteById(id);

        return REGISTRATION_DELETED;
    }

    private boolean registrationExists(RegistrationDTO registrationDTO) {
        return registrationRepository.existsByEmailAndTournamentId(
                registrationDTO.getEmail(),
                registrationDTO.getTournament().getId()
        );
    }

    private RegistrationDTO createSimpleRegistrationDTO(Registration registration) {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setId(registration.getId());
        dto.setParticipantName(registration.getParticipantName());
        dto.setEmail(registration.getEmail());
        dto.setTeamName(registration.getTeamName());
        return dto;
    }

    private RegistrationDTO findRegistrationById(Long id) {
        return registrationMapper.mapTo(
                registrationRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException(REGISTRATION_NOT_FOUND))
        );
    }

    private TournamentDTO getTournamentForRegistration(RegistrationDTO registrationDTO) {
        return tournamentService.getTournamentById(registrationDTO.getTournament().getId());
    }

    private void updateTournamentWithRegistration(TournamentDTO tournamentDTO) {
        tournamentService.updateTournament(tournamentDTO.getId(), tournamentDTO);
    }
}