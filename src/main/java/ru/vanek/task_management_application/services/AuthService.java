package ru.vanek.task_management_application.services;

import org.springframework.stereotype.Component;
import ru.vanek.task_management_application.dtos.requests.JwtRequest;
import ru.vanek.task_management_application.dtos.requests.UserRequest;
import ru.vanek.task_management_application.dtos.responses.JwtResponse;
import ru.vanek.task_management_application.dtos.responses.UserResponse;

@Component
public interface AuthService {
     JwtResponse createAuthToken(JwtRequest authRequest);
     UserResponse createNewUser(UserRequest userRequest);
}
