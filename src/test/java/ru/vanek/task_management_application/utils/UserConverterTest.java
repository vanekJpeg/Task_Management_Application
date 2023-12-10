package ru.vanek.task_management_application.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vanek.task_management_application.dtos.requests.UserRequest;
import ru.vanek.task_management_application.dtos.responses.UserResponse;
import ru.vanek.task_management_application.models.Comment;
import ru.vanek.task_management_application.models.Task;
import ru.vanek.task_management_application.models.User;
import ru.vanek.task_management_application.utils.impl.UserConverterImpl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserConverterTest {

    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserConverterImpl converter;
    @Test
    void convertUserToResponse() {
        //given
        User user1 = new User
                (1,"Author1", "AuthorPassword_1", "Author1@mail.ru");
        Task task = new Task();
        Comment comment = new Comment();
        user1.setComments(List.of(comment));
        user1.setToDoTasks(List.of(task));
        UserResponse toReturnIfHasCommentsAndTasks=  new UserResponse(user1.getId(),user1.getUsername(),
                user1.getEmail(),

                user1.getCreatedTasks()==null?(Collections.emptyList()):
                        user1.getCreatedTasks().stream().map(Task::getId).collect(Collectors.toList()),

                user1.getToDoTasks()==null?(Collections.emptyList()):
                        user1.getToDoTasks().stream().map(Task::getId).collect(Collectors.toList()),

                user1.getComments()==null?(Collections.emptyList()):
                        user1.getComments().stream().map(Comment::getId).collect(Collectors.toList()));

        User user2 = new User
                (2,"Author2", "AuthorPassword_2", "Author2@mail.ru");
        UserResponse toReturnIfNotHasCommentsAndTasks=  new UserResponse(user2.getId(),user2.getUsername(),
                user2.getEmail(),

                user2.getCreatedTasks()==null?(Collections.emptyList()):
                        user2.getCreatedTasks().stream().map(Task::getId).collect(Collectors.toList()),

                user2.getToDoTasks()==null?(Collections.emptyList()):
                        user2.getToDoTasks().stream().map(Task::getId).collect(Collectors.toList()),

                user2.getComments()==null?(Collections.emptyList()):
                        user2.getComments().stream().map(Comment::getId).collect(Collectors.toList()));
        //when
        var responseEntity1 = this.converter.convertUserToResponse(user1);
        var responseEntity2 = this.converter.convertUserToResponse(user2);
        // then
        assertNotNull(responseEntity1);
        assertEquals(toReturnIfHasCommentsAndTasks, responseEntity1);
        assertNotNull(responseEntity2);
        assertEquals(toReturnIfNotHasCommentsAndTasks, responseEntity2);
    }


    @Test
    void convertToUser() {
        //given

        UserRequest userRequest = new UserRequest("Name","password","password","email");
        doReturn("encodedPassword").when(this.passwordEncoder).encode(userRequest.getPassword());
        User user = new User(0,userRequest.getUsername(),"encodedPassword",userRequest.getEmail());

        //when
        var responseEntity1 = this.converter.convertToUser(userRequest);
        // then
        assertNotNull(responseEntity1);
        assertEquals(user, responseEntity1);
    }
}