package ru.vanek.task_management_application.utils.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.vanek.task_management_application.dtos.requests.TaskRequest;
import ru.vanek.task_management_application.dtos.responses.TaskResponse;
import ru.vanek.task_management_application.models.Task;
import ru.vanek.task_management_application.models.User;
import ru.vanek.task_management_application.utils.CommentConverter;
import ru.vanek.task_management_application.utils.TaskConverter;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class TaskConverterImpl implements TaskConverter {
    private final CommentConverter commentConverter;
    public TaskConverterImpl(@Lazy CommentConverter commentConverter) {
        this.commentConverter = commentConverter;
    }
    public TaskResponse convertToResponse(Task task){
        return new TaskResponse(task.getId(),task.getHeader(),task.getDescription()
                ,task.getStatus().getStringValueOfStatus(),
                task.getAuthor()==null
                        ?"Автор удален":task.getAuthor().getEmail()
                ,task.getExecutor()==null
                ?"Исполнитель удален":task.getExecutor().getEmail(),
                task.getComments()==null?(Collections.emptyList()):
                        task.getComments().stream().map(commentConverter::convertToResponse).collect(Collectors.toList()),task.getCreatedAt());
    }
}
