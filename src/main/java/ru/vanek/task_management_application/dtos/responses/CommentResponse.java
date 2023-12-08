package ru.vanek.task_management_application.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class CommentResponse {
    private int id;
    private String text;
    private String authorEmail;
    private Date createdAt;

}
