package com.aseds.tournamentmicroservice.repository;


import com.aseds.tournamentmicroservice.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament,Long>{
}
