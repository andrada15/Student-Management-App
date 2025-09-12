package ro.scoalainformala.studentmgmt.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtService {

    private final SecretKey key;


    public JwtService () {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(final String username, final List<String> roles) {
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();

    }

    public Claims validateToken(final String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();


    }

    public String extractUsername(final String token) {
        return validateToken(token).getSubject();
    }

    public boolean isTokenValid(final String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token) {
        return validateToken(token).getExpiration();
    }
}
