package ru.vanek.task_management_application.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
public class TaskResponse {
    private int id;
    private String header;
    private String description;
    private String statusDescription;
    private UserResponse author;
    private UserResponse executor;
    private List<CommentResponse> commentsResponses;
    private Date createdAt;
}
