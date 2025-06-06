package com.aseds.tournamentmicroservice.repository;

import com.aseds.tournamentmicroservice.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration,Long> {
    boolean existsByEmailAndTournamentId(String email, Long tournamentId);
}
