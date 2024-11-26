package com.hexagonal.user_auto.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("auto-secret-key-auto-secret-key-auto-secret-key".getBytes());
    private static final long EXPIRATION_TIME = 86400000; // 24 horas

    /**
     * Gera um token JWT para um determinado usuário.
     *
     * @param username O nome de usuário para o qual o token será gerado.
     * @return O token JWT gerado.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiração de 24 horas
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Usa HMAC SHA-256 para assinar o token
                .compact(); // Compacta todas as partes do token em um formato JWT completo
    }

    /**
     * Extrai o nome de usuário de um token JWT.
     *
     * @param token O token JWT.
     * @return O nome de usuário extraído do token.
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Valida se o token é válido para um determinado nome de usuário.
     *
     * @param token    O token JWT a ser validado.
     * @param username O nome de usuário que se espera estar no token.
     * @return true se o token for válido, false caso contrário.
     */
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (username.equals(extractedUsername) && !isTokenExpired(token));
    }

    /**
     * Verifica se um token JWT está expirado.
     *
     * @param token O token JWT.
     * @return true se o token estiver expirado, false caso contrário.
     */
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    /**
     * Extrai todas as Claims de um token JWT.
     *
     * @param token O token JWT.
     * @return As Claims extraídas do token.
     */
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
