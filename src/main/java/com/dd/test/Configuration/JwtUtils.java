package com.dd.test.Configuration;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long keyExpiration;


    public String GenerateToken(String username){
        Map<String,Object> claims = new HashMap<>();
        return CreateToken(claims,username);
    }


    private String CreateToken(Map<String,Object> claims, String username) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(username)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + keyExpiration))
                   .signWith(getSignKey(),SignatureAlgorithm.HS256)
                   .compact();
    }


    private Key getSignKey() {
        byte[] keyBytes = secretKey.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public Boolean ValidateToken(String token,UserDetails userDetails){
       String username= extractUsername(token);
       return (username.equals(userDetails.getUsername()) && !isTokenExpire(token));
    }


    private boolean isTokenExpire(String token) {
       return extractTokenExpire(token).before(new Date());
    }


    public String extractUsername(String token) {
       return extractClaim(token,Claims::getSubject);
    }
    private Date extractTokenExpire(String token) {
       return extractClaim(token,Claims::getExpiration);
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
       final Claims claims = extractAllClaims(token);
       return claimResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSignKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }
}
