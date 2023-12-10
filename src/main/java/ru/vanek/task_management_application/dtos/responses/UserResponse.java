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
    private List<Integer> createdTasksId;
    private List<Integer> toDoTasksId;
    private List<Integer> commentsId;

    public UserResponse(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
