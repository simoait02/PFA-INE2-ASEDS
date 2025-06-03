package com.aseds.tournamentmicroservice.repository;

import com.aseds.tournamentmicroservice.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {
}
