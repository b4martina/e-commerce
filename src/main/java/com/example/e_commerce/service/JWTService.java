package com.example.e_commerce.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.util.Date;

//part 2 creating jwt token just created customuserdetailservice
@Service
public class JWTService {

    private final String SECRET_KEY = "keykeykeykeykeykeykeykey28282828";

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));


    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 18282828))
                .signWith(key, Jwts.SIG.HS256)
                .compact();

    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();}


    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token))
                && extractClaims(token).getExpiration().after(new Date());}
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();}

}
