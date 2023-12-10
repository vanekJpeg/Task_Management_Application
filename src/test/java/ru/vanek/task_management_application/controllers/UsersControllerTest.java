package ru.vanek.task_management_application.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import ru.vanek.task_management_application.dtos.requests.JwtRequest;
import ru.vanek.task_management_application.dtos.requests.UserRequest;
import ru.vanek.task_management_application.dtos.responses.JwtResponse;
import ru.vanek.task_management_application.dtos.responses.UserResponse;
import ru.vanek.task_management_application.services.AuthService;
import ru.vanek.task_management_application.services.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
@ExtendWith(MockitoExtension.class)
class UsersControllerTest {
    @Mock
    UserService userService;
    @Mock
    AuthService authService;
    @Mock
    MessageSource messageSource;
    @InjectMocks
    UsersController usersController;
    private static List<UserResponse> getUserResponses() {
        var author = new UserResponse
                (1, "Author", "Author@mail.ru");
        var executor = new UserResponse
                (2, "Executor",  "Executor@mail.ru");

        return (List.of(author, executor));
    }
    @Test
    void createAuthToken() {
        // given
        JwtRequest jwtRequest = new JwtRequest("Author@mail.ru","Author123");
        JwtResponse jwtResponse = new JwtResponse("$2a$10$dzl3bQHE/9TQButInq2Itu/iTq.20BcHnKn8EsM5QcRVqmT4RzERi");

        doReturn(jwtResponse).when(this.authService).createAuthToken(jwtRequest);
        // when
        var responseEntity = this.usersController.createAuthToken(jwtRequest);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(jwtResponse, responseEntity.getBody());

    }

    @Test
    void createNewUser() {
        // given
        List<UserResponse> userResponses = getUserResponses();
        UserRequest userRequest= new UserRequest();
        doReturn(userResponses.get(1)).when(this.authService).createNewUser(userRequest);
        // when
        var responseEntity = this.usersController.createNewUser(userRequest,UriComponentsBuilder.newInstance());

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(userResponses.get(1), responseEntity.getBody());
    }

    @Test
    void getUsers() {
        // given
        List<UserResponse> userResponses = getUserResponses();

        doReturn(userResponses).when(this.userService).findAll(0);
        // when
        var responseEntity = this.usersController.getUsers(0);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(userResponses, responseEntity.getBody());
    }

    @Test
    void show() {
        // given
        List<UserResponse> userResponses = getUserResponses();

        doReturn(userResponses.get(0)).when(this.userService).findOne(1);
        // when
        var responseEntity = this.usersController.show(1);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(userResponses.get(0), responseEntity.getBody());
    }
}