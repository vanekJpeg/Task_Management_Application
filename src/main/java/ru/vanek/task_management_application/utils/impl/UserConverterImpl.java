package ru.vanek.task_management_application.utils.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.vanek.task_management_application.dtos.requests.UserRequest;
import ru.vanek.task_management_application.dtos.responses.UserResponse;
import ru.vanek.task_management_application.models.Comment;
import ru.vanek.task_management_application.models.Task;
import ru.vanek.task_management_application.models.User;
import ru.vanek.task_management_application.utils.CommentConverter;
import ru.vanek.task_management_application.utils.TaskConverter;
import ru.vanek.task_management_application.utils.UserConverter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverterImpl implements UserConverter {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserConverterImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

    }
    public UserResponse convertUserToResponse(User user){
        return new UserResponse(user.getId(),user.getUsername(),
                user.getEmail(),

                user.getCreatedTasks()==null?(Collections.emptyList()):
                        user.getCreatedTasks().stream().map(Task::getId).collect(Collectors.toList()),

                user.getToDoTasks()==null?(Collections.emptyList()):
                        user.getToDoTasks().stream().map(Task::getId).collect(Collectors.toList()),

                user.getComments()==null?(Collections.emptyList()):
                        user.getComments().stream().map(Comment::getId).collect(Collectors.toList()));
    }
    public User convertToUser(UserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        return user;
    }
}
