
package com.aseds.tournamentmicroservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tournaments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDate date;
    private String location;

    @ManyToMany
    private List<Game> games;

    private String description;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Registration> registrations;
}