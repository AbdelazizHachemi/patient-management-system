package abdelaziz.project.auth_service.controller;

import org.springframework.web.bind.annotation.RestController;

import abdelaziz.project.auth_service.dto.LoginRequestDTO;
import abdelaziz.project.auth_service.dto.LoginResponseDTO;
import abdelaziz.project.auth_service.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AuthController {
    private final AuthService authservice;

    public AuthController(AuthService authservice) {
        this.authservice = authservice;
    }

    @Operation(summary = "Login endpoint", description = "Authenticate user and return a JWT token")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        // TODO: process POST request

        Optional<String> tokenOptional = authservice.authenticate(loginRequest);

        if (tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenOptional.get();

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @Operation(summary = "Validate token", description = "Validate JWT token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authservice.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

}
