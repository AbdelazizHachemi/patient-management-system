package abdelaziz.project.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // This bean defines the HTTP security rules for the application.
    // For now, all incoming requests are allowed without authentication,
    // and CSRF protection is disabled to keep the API simple.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    // Password hashing bean used to encode user passwords before saving them.
    // BCrypt is a strong, adaptive hashing algorithm that helps protect credentials.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
