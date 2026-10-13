package abdelaziz.project.auth_service.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import abdelaziz.project.auth_service.dto.LoginRequestDTO;
import abdelaziz.project.auth_service.model.User;

@Service 
public class AuthService {
    
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        
        Optional<String> token = userService.findByEmail(loginRequestDTO.getEmail())
        .filter(u -> passwordEncoder.matchs(loginRequestDTO.getPassword(),u.getPassword()))
        .map(u -> jwtUtil.generateToken(u.getEmail(),u.getRole()));
        
        
        return token;
    }
}
