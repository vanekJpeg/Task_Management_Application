package ru.vanek.task_management_application.utils.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.vanek.task_management_application.dtos.requests.CommentRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;
import ru.vanek.task_management_application.models.Comment;
import ru.vanek.task_management_application.utils.CommentConverter;
import ru.vanek.task_management_application.utils.UserConverter;

@Component
public class CommentConverterImpl implements CommentConverter {
    @Override
    public CommentResponse convertToResponse(Comment comment) {
        return new CommentResponse(comment.getId(),comment.getText(),comment.getAuthor().getEmail(),comment.getCreatedAt());
    }

    @Override
    public Comment convertToComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setText(commentRequest.getText());
        return comment;
    }
}
