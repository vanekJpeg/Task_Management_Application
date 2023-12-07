package ru.vanek.task_management_application.utils.impl;

import org.springframework.stereotype.Component;
import ru.vanek.task_management_application.dtos.requests.CommentRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;
import ru.vanek.task_management_application.models.Comment;
import ru.vanek.task_management_application.utils.CommentConverter;
import ru.vanek.task_management_application.utils.UserConverter;

@Component
public class CommentConverterImpl implements CommentConverter {
    private final UserConverter userConverter;

    public CommentConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }
    @Override
    public CommentResponse convertToResponse(Comment comment) {
        return new CommentResponse(comment.getId(),comment.getText(),userConverter.convertUserToResponse(comment.getAuthor()),comment.getCreatedAt());
    }

    @Override
    public Comment convertToComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setText(commentRequest.getText());
        return comment;
    }
}
