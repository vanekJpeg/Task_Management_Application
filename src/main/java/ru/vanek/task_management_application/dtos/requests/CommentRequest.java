package ru.vanek.task_management_application.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommentRequest {
    private String text;
}
