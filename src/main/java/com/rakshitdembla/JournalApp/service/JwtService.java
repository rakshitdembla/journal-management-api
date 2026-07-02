package com.rakshitdembla.JournalApp.service;

import com.rakshitdembla.JournalApp.cache.AppConfigCache;
import com.rakshitdembla.JournalApp.utility.PlaceHolder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final AppConfigCache appConfigCache;
    private final String jwtSecret;
    private final long jwtLoginExpiry;

    public JwtService(AppConfigCache appConfigCache) {
        this.appConfigCache = appConfigCache;

        this.jwtSecret = appConfigCache.cacheMap.get(PlaceHolder.JWT_SECRET_KEY);

        this.jwtLoginExpiry = Long.parseLong(
                appConfigCache.cacheMap.get(PlaceHolder.JWT_LOGIN_EXPIRY)
        );
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails,
                                Map<String, Object> claims) {

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtLoginExpiry))
                .signWith((SecretKey) getSignInKey())
                .compact();
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extract username (sub claim)
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract token expiration date
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // Check whether the JWT has expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate the JWT
    public boolean isTokenValid(String token,
                                UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

}