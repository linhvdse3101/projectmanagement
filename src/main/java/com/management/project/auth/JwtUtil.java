package com.management.project.auth;

import com.management.project.responses.UserAccountDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    @FunctionalInterface
    public interface ClaimResolver<T>{
        T resolver(Claims claims);
    }
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token,ClaimResolver<T> claimResolver ){
        final Claims claims = extractAllClaims(token);
        return claimResolver.resolver(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token) // Sửa parseClaimsJwt thành parseClaimsJws
                    .getBody();
        } catch (io.jsonwebtoken.JwtException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails account){
        final String username = extractUsername(token);
        try {
            return username.equals(account.getUsername()) && !isTokenExpired(token);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    public String refreshToken(UserAccountDto user){
        Map<String, Object> claimsInfo = new HashMap<>();
        return generateToken(user, claimsInfo, refreshTokenExpiration);
    }

    public String generateToken(UserAccountDto account, Map<String, Object> claimsInfo, long expirationToken){

        claimsInfo.put("role", account.getRoles().toString()); // custom here

        return Jwts.builder().setClaims(claimsInfo)
                .setSubject(account.getUsername())    // cat
//                .claims(claimsInfo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()  + expirationToken))
                .signWith(getSignInKey())
                .compact();
    }
    public String generateToken(UserAccountDto acc) {
        return generateToken(acc,new HashMap<>(), expiration);
    }

    private Key getSignInKey(){
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }
}