package abdelaziz.project.auth_service.services;

import abdelaziz.project.auth_service.repository.UserRepository;
import java.util.Optional;

import org.springframework.stereotype.Service;

import abdelaziz.project.auth_service.model.User;

@Service 
public class UserService {
    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        // Implement the logic to find a user by email
        return userRepository.findByEmail(email);
    }
}
