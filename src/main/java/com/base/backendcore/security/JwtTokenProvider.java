package com.base.backendcore.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @PostConstruct
    public void init() {
    }

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .claim("role", userPrincipal.getAuthorities().stream().findFirst().get().getAuthority())
                .claim("unique_name", userPrincipal.getUsername())
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            System.out.print("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.print("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.print("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.print("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.print("JWT claims string is empty.");
        }
        return false;
    }
}
