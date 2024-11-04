package org.example.csrfcorsimplementationdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CsrfTokenRepository csrfTokenRepository;

    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception{

        http.cors(c->{
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(
                        List.of("examples.com", "api.examples.com")
                );
                config.setAllowedMethods(
                        List.of("GET","POST","PUT","DELETE")
                );
                config.setAllowedHeaders(List.of("*"));
                return config;
            };
            c.configurationSource(source);
        });
        http.csrf(c->c.csrfTokenRepository(csrfTokenRepository)
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
        );

        http.authorizeHttpRequests(
                c->c.anyRequest().permitAll()
        );

        return http.build();
    }
}
