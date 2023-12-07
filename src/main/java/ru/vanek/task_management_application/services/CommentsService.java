package ru.vanek.task_management_application.services;

import org.springframework.stereotype.Service;
import ru.vanek.task_management_application.dtos.requests.CommentRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;

import java.util.List;
@Service
public interface CommentsService {
    public List<CommentResponse> findAll(int page);
    public CommentResponse findOne(int id);
    public CommentResponse publishComment(CommentRequest commentRequest,int taskId);
    public void update(int commentId,CommentRequest commentRequest);
    public void delete(int id);
    public boolean isEnoughRules(int userId, String username);
}
