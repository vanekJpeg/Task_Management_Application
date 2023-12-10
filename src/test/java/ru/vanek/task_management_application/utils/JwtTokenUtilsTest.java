package ru.vanek.task_management_application.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.vanek.task_management_application.models.Role;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class JwtTokenUtilsTest {

    String secret;
    Duration jwtLifetime;
    Key hmacKey;
    UserDetails userDetails;
    @BeforeEach
    public void init(){
        secret="984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gf";
        jwtLifetime = Duration.ofMinutes(30);
        hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
        ru.vanek.task_management_application.models.User user = new ru.vanek.task_management_application.models.User();
        user.setEmail("testemail@mail.ru");
        user.setPassword("Test_password1");
        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(List.of(role));
        userDetails=new User(user.getEmail(),user.getPassword()
                ,user.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList()));
    }

@InjectMocks
JwtTokenUtils jwtTokenUtils;

    @Test
    void generateToken() {
        //given
        Map<String ,Object> claims =new HashMap<>();
        List<String> roleList =userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        claims.put("roles",roleList);

        Date issuedDate= new Date(1212121212121L);
        Date expiredDate = new Date(issuedDate.getTime()+jwtLifetime.toMillis());

        String token ="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGVzdGVtYWlsQG1haWwucnUiLCJpYXQiOjEyMTIxMjEyMTIsImV4cCI6MTIxMjEyMzAxMn0.d3JVxuk-AdUzb53Kqhzn9HIqaM5vu4Q2O-DQ62bvt9A";
        // when
        var responseEntity= Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(hmacKey)
                .compact();
        // then
        assertNotNull(responseEntity);
        assertEquals(token, responseEntity);
    }
}