package abdelaziz.project.auth_service.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Table (name="users")
@Data 
public class User {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column (name = "email", nullable = false, unique = true)
    private String email;

    @Column (name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;
}
