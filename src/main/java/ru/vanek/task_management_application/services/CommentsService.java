package ru.vanek.task_management_application.services;

import org.springframework.stereotype.Service;
import ru.vanek.task_management_application.dtos.requests.CommentRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;


@Service
public interface CommentsService {
     CommentResponse findOne(int id);
     CommentResponse publishComment(CommentRequest commentRequest,int taskId,String userEmail);
     void update(int commentId,CommentRequest commentRequest,String userEmail);
     void delete(int id,String userEmail);
     boolean isEnoughRules(int userId, String userEmail);
}
