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
    private String authorName;
    private String executorName;
    private List<CommentResponse> comments;
    private Date createdAt;

    public TaskResponse() {
    }
}
