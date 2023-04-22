package com.oauth2security.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/20/23
 */

@Component
public class JWTGenerator {

    @Value("${jwt.expiration}")
    private long JWTExpiration;

    @Value("${jwt.secret}")
    private String JWTSecret;

    private ObjectMapper objectMapper;

    @Autowired
    JWTGenerator(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    private void init() throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(JWTSecret.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] encodedSecretKeyBytes = mac.doFinal();
        //JWTSecret key is used to both sign and verify JWT token which is symmetric key cryptography
        this.JWTSecret = Base64.encodeBase64String(encodedSecretKeyBytes);
    }

    public String generateJWTToken(Authentication authentication) throws JsonProcessingException {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + JWTExpiration);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String authoritiesJsonValue = objectMapper.writeValueAsString((authentication.getAuthorities()));
        Claims claims = Jwts.claims().setSubject(authentication.getName());
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("email", authentication.getName());
        claimsMap.put("user", authentication.getName());
        claimsMap.put("authorities", authoritiesJsonValue);
        claims.putAll(claimsMap);

        JwtBuilder jwts = Jwts.builder();

        String token = jwts
                .setSubject(userDetails.getUsername())
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, JWTSecret)
                .compact();
        return token;
    }

    public String getUsernameFromJWTToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWTSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateJWTToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(JWTSecret).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
