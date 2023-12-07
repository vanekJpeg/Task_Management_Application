package ru.vanek.task_management_application.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vanek.task_management_application.dtos.requests.JwtRequest;
import ru.vanek.task_management_application.dtos.requests.UserRequest;
import ru.vanek.task_management_application.dtos.responses.UserResponse;
import ru.vanek.task_management_application.services.AuthService;
import ru.vanek.task_management_application.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;
    private final AuthService authService;
    public UsersController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@Valid @RequestBody JwtRequest authRequest){
        return authService.createAuthToken(authRequest);
    }
    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody UserRequest userRequest) {
        return authService.createNewUser(userRequest);
    }
    @GetMapping()
    public ResponseEntity<UserResponse> getUsers(@RequestParam(value = "page",required = false, defaultValue = "0" ) int page) {
        return ResponseEntity.ok((UserResponse)userService.findAll(page));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> show(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.findOne(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> edit( @PathVariable("id") int id,Principal principal, @RequestBody UserRequest userRequest) {
        //todo переделать principal
        userService.update(id,userRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id, Principal principal) {
        userService.delete(id);//todo authorisatiomn
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

