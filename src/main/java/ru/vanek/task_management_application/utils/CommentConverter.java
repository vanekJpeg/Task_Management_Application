package ru.vanek.task_management_application.utils;

import ru.vanek.task_management_application.dtos.requests.CommentRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;
import ru.vanek.task_management_application.models.Comment;


public interface CommentConverter {
    public CommentResponse convertToResponse(Comment comment);
    public Comment convertToComment(CommentRequest commentRequest);
}
