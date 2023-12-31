package ru.vanek.task_management_application.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import ru.vanek.task_management_application.dtos.requests.JwtRequest;
import ru.vanek.task_management_application.dtos.requests.UserRequest;
import ru.vanek.task_management_application.dtos.responses.JwtResponse;
import ru.vanek.task_management_application.dtos.responses.UserResponse;
import ru.vanek.task_management_application.exceptions.AuthException;
import ru.vanek.task_management_application.models.User;
import ru.vanek.task_management_application.services.AuthService;
import ru.vanek.task_management_application.services.UserService;
import ru.vanek.task_management_application.utils.JwtTokenUtils;
import ru.vanek.task_management_application.utils.UserConverter;


@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserConverter userConverter;
    private final AuthenticationManager authenticationManager;
    public AuthServiceImpl(UserService userService, JwtTokenUtils jwtTokenUtils, UserConverter userConverter, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userConverter = userConverter;
        this.authenticationManager = authenticationManager;
    }
    public JwtResponse createAuthToken(@RequestBody JwtRequest authRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getEmail());
        String token= jwtTokenUtils.generateToken(userDetails);
        return new JwtResponse(token);
    }
    public UserResponse createNewUser(@RequestBody UserRequest userRequest) {
        if(!userRequest.getPassword().equals(userRequest.getConfirmPassword())){
            throw new AuthException("Пароли не совпадают");
        }if(userService.findByEmail(userRequest.getEmail())!=null){
            throw new AuthException("Пользователь с указанным email'ом уже существует");
        }
        User user= userService.create(userRequest);
        return userConverter.convertUserToResponse(user);
    }
}