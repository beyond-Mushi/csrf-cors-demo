package org.example.csrfcorsimplementationdemo.repository;

import org.example.csrfcorsimplementationdemo.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomJpaRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findTokenByIdentifier(String identifier);
}
