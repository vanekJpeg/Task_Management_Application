package ru.vanek.task_management_application.services;

import org.springframework.stereotype.Service;
import ru.vanek.task_management_application.dtos.requests.CommentRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;

import java.util.List;
@Service
public interface CommentsService {
    public CommentResponse findOne(int id);
    public CommentResponse publishComment(CommentRequest commentRequest,int taskId,String userEmail);
    public void update(int commentId,CommentRequest commentRequest,String userEmail);
    public void delete(int id,String userEmail);
    public boolean isEnoughRules(int userId, String userEmail);
}
