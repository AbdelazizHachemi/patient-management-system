package abdelaziz.project.auth_service.controller;

import org.springframework.web.bind.annotation.RestController;

import abdelaziz.project.auth_service.dto.LoginRequestDTO;
import abdelaziz.project.auth_service.dto.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
public class AuthController {
    
    @Operation (summary = "Login endpoint", description = "Authenticate user and return a JWT token")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        //TODO: process POST request
        
        Optional<String> tokenOptional = authservice.authenticate(loginRequest);

        if(tokenOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenOptional.get();


        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    
}
