package ru.vanek.task_management_application.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.vanek.task_management_application.dtos.requests.JwtRequest;
import ru.vanek.task_management_application.models.Role;
import ru.vanek.task_management_application.services.UserService;
import ru.vanek.task_management_application.utils.JwtTokenUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    UserService userService;
    @Mock
    JwtTokenUtils jwtTokenUtils;
    @Mock
    AuthenticationManager authenticationManager;
    @InjectMocks
    AuthServiceImpl authService;
    @Test
    void createAuthToken() {
        // given
        JwtRequest authRequest = new JwtRequest("testemail@mail.ru","Test_password1");
        ru.vanek.task_management_application.models.User user = new ru.vanek.task_management_application.models.User();
        user.setEmail("testemail@mail.ru");
        user.setPassword("Test_password1");
        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(List.of(role));
        UserDetails userDetails=new User(user.getEmail(),user.getPassword()
                ,user.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList()));
        doReturn(userDetails).when(this.userService).loadUserByUsername("testemail@mail.ru");
        doReturn(("eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGVzdGVtYWlsQG1haWwucnUiLCJpYXQiOjEyMTIxMjEyMTIsImV4cCI6MTIxMjEyMzAxMn0.d3JVxuk-AdUzb53Kqhzn9HIqaM5vu4Q2O-DQ62bvt9A"))
                .when(this.jwtTokenUtils).generateToken(userDetails);
        String token ="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGVzdGVtYWlsQG1haWwucnUiLCJpYXQiOjEyMTIxMjEyMTIsImV4cCI6MTIxMjEyMzAxMn0.d3JVxuk-AdUzb53Kqhzn9HIqaM5vu4Q2O-DQ62bvt9A";
        // when
        var responseEntity = this.authService.createAuthToken(authRequest);
        // then
        assertNotNull(responseEntity);
        assertEquals(token, responseEntity.getToken());
    }
}