package ru.vanek.task_management_application.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.vanek.task_management_application.dtos.requests.JwtRequest;
import ru.vanek.task_management_application.dtos.requests.UserRequest;
@Component
public interface AuthService {
    public ResponseEntity<?> createAuthToken(JwtRequest authRequest);
    public ResponseEntity<?> createNewUser(UserRequest userRequest);
}
