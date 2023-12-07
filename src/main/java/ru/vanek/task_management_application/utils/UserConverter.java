package ru.vanek.task_management_application.utils;

import org.springframework.stereotype.Component;
import ru.vanek.task_management_application.dtos.requests.UserRequest;
import ru.vanek.task_management_application.dtos.responses.UserResponse;
import ru.vanek.task_management_application.models.User;
@Component
public interface UserConverter {
    public UserResponse convertUserToResponse(User user);
    public User convertToUser(UserRequest userRequest);
}
