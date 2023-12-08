package ru.vanek.task_management_application.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vanek.task_management_application.dtos.requests.CommentRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;
import ru.vanek.task_management_application.models.Comment;
import ru.vanek.task_management_application.repositories.CommentsRepository;
import ru.vanek.task_management_application.repositories.TasksRepository;
import ru.vanek.task_management_application.repositories.UsersRepository;
import ru.vanek.task_management_application.services.CommentsService;
import ru.vanek.task_management_application.utils.CommentConverter;

import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsServiceImpl implements CommentsService {


    private final CommentsRepository commentsRepository;
    private final TasksRepository tasksRepository;
    private final UsersRepository usersRepository;
    private final CommentConverter commentConverter;



    @Override
    public List<CommentResponse> findAll(int page) {
        return null;
    }

    @Override
    public CommentResponse findOne(int id) {
        return commentConverter.convertToResponse(commentsRepository.findById(id).orElseThrow());//todo exception comment not dound
    }

    @Override
    public CommentResponse publishComment(CommentRequest commentRequest, int taskId,String userEmail) {
        Comment comment = commentConverter.convertToComment(commentRequest);
        comment.setAuthor(usersRepository.findByEmail("dsds").orElseThrow());//todo principal to user and exception
        comment.setCreatedAt(new Date());
        comment.setCommentedTask(tasksRepository.findById(taskId).orElseThrow());//todo exception
        return commentConverter.convertToResponse(comment);
    }

    @Override
    public void update(int commentId, CommentRequest commentRequest,String userEmail) {

        if(isEnoughRules(commentId,userEmail)){
            Comment updatedComment = commentsRepository.findById(commentId).orElseThrow();//todo exception
            Comment changes = commentConverter.convertToComment(commentRequest);
            updatedComment.setText(changes.getText());
        } else throw new RuntimeException();
    }

    @Override
    public void delete(int commentId,String userEmail) {
        if(isEnoughRules(commentId,userEmail)){
            commentsRepository.deleteById(commentId);
        } else throw new RuntimeException();

    }

    @Override
    public boolean isEnoughRules(int commentId, String email) {
        return email.equals(commentsRepository.findById(commentId).orElseThrow().getAuthor().getEmail());//todo exception
    }
}
