package ru.vanek.task_management_application.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class UserResponse {
    private int id;
    private String username;
    private String email;
    private List<TaskResponse> createdTasks;
    private List<TaskResponse> toDoTasks;
    private List<CommentResponse> comments;
}
