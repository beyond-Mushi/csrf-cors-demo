package org.example.csrfcorsimplementationdemo.repository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.csrfcorsimplementationdemo.Entity.Token;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    private final CustomJpaRepository jpaRepository;

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
    }

    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse response) {
        String identifier = request.getHeader("X-IDENTIFIER");
        Optional<Token> existToken = jpaRepository.findTokenByIdentifier(identifier);

        if (existToken.isPresent()) {
            Token token = existToken.get();
            token.setToken(csrfToken.getToken());
        } else {
            Token token = new Token();
            token.setIdentifier(identifier);
            token.setToken(csrfToken.getToken());
            jpaRepository.save(token);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String identifier = request.getHeader("X-IDENTIFIER");
        Optional<Token> existToken = jpaRepository.findTokenByIdentifier(identifier);
        if (existToken.isPresent()) {
            Token token = existToken.get();
            return new DefaultCsrfToken(
                    "X-CSRF-TOKEN",
                    "_csrf",
                    token.getToken()
            );
        }
        return null;
    }
}
