package abdelaziz.project.auth_service.Utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    // Secret key used to sign and verify JWT tokens.
    // It is created from the configured Base64-encoded secret value.
    private final Key jwtSecret;

    // The constructor reads the application property "jwt.secret",
    // decodes it from Base64, and turns it into a valid HMAC signing key.
    public JwtUtils(@Value("${jwt.secret}") String jwtSecret) {
        byte[] decodedSecret = Base64.getDecoder()
                .decode(jwtSecret.getBytes(StandardCharsets.UTF_8));

        this.jwtSecret = Keys.hmacShaKeyFor(decodedSecret);
    }

    // Creates a JWT containing the user's email and role.
    // The token is signed with the app secret and expires after 10 hours.
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(jwtSecret)
                .compact();
    }

    // Validates the JWT token by parsing it with the secret key.
    // If the token is invalid or expired, an exception will be thrown.
    public void validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) jwtSecret)
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }
    }
}
