package ru.vanek.task_management_application.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
@Component
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    private  String secret;
    @DurationUnit(ChronoUnit.MINUTES)
    private Duration jwtLifetime;
    private Key hmacKey;
    @PostConstruct
    public void init(){
        jwtLifetime = Duration.ofMinutes(10);
        hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(UserDetails userDetails){
        Map<String ,Object> claims =new HashMap<>();
        Date issuedDate= new Date();
        Date expiredDate = new Date(issuedDate.getTime()+jwtLifetime.toMillis());

        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(hmacKey)
                .compact();
    }

    public String getUsername(String token){
        return getAllClaimsFromToken(token).getSubject();
    }
    public List<String> getRoles(String token){
        return getAllClaimsFromToken(token).get("roles",List.class);
    }
    public Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(token).getBody();
    }
}

