package com.thinkify.events.config.impl;

import com.thinkify.events.config.JWTService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTServiceImpl implements JWTService {
    private static Logger LOGGER = LoggerFactory.getLogger(JWTServiceImpl.class);
    private static final String SECRET_KEY = "e2d2d43ff9ab206a21476ff3fe71db5ebc9501c2be6503925be9c8a18e0b5005";

    @Override
    public String extractUserName(String jwt) throws JwtException, IllegalArgumentException{
        return extractClaim(jwt, claims -> claims.get("email", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws JwtException, IllegalArgumentException{
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new java.util.HashMap<>(), userDetails);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiration = new Date(now + 1000 * 60 * 24);

        extraClaims.put("email", userDetails.getUsername());
        extraClaims.put("issued_at", issuedAt.getTime());
        extraClaims.put("expiry_at", expiration.getTime());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, String userNameHeader) {
        try{
            String userName = extractUserName(token);
            return isSubjectMatching(userName, userNameHeader) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            LOGGER.error("Caught exception in jwt parsing: " + e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) throws JwtException, IllegalArgumentException{
        boolean tokenExpired = extractExpiration(token).before(new Date());
        LOGGER.info("token expired - {}", tokenExpired);
        return tokenExpired;
    }

    private boolean isSubjectMatching(String subjectJwt, String subjectHeader){
        boolean subjectMatching = !subjectHeader.isBlank()
                                        && !subjectJwt.isBlank()
                                        && subjectJwt.equals(subjectHeader);
        LOGGER.info("subject matching - {}", subjectMatching);
        return subjectMatching;
    }

    private Date extractExpiration(String token) throws JwtException, IllegalArgumentException{
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) throws JwtException, IllegalArgumentException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}


