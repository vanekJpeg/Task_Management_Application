package ru.vanek.task_management_application.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.vanek.task_management_application.dtos.requests.JwtRequest;
import ru.vanek.task_management_application.dtos.requests.UserRequest;
import ru.vanek.task_management_application.dtos.responses.JwtResponse;
import ru.vanek.task_management_application.dtos.responses.UserResponse;

@Component
public interface AuthService {
    public JwtResponse createAuthToken(JwtRequest authRequest);
    public UserResponse createNewUser(UserRequest userRequest);
}
