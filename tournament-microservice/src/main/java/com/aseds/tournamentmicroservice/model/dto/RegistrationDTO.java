package com.aseds.tournamentmicroservice.model.dto;

import com.aseds.tournamentmicroservice.model.Tournament;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationDTO {
    private Long id;
    private String participantName;
    private String email;
    private String teamName;
    private Tournament tournament;
}
