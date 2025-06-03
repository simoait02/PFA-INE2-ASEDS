package com.aseds.tournamentmicroservice.model.dto;


import com.aseds.tournamentmicroservice.model.Game;
import com.aseds.tournamentmicroservice.model.Registration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TournamentDTO {
    private Long id;
    private String title;
    private LocalDate date;
    private String location;
    private List<Game> games;
    private String description;
    private List<Registration> registrations;
}
