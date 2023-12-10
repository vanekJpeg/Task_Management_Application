package ru.vanek.task_management_application.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vanek.task_management_application.dtos.requests.CommentRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;
import ru.vanek.task_management_application.exceptions.NotEnoughRulesException;
import ru.vanek.task_management_application.models.Comment;
import ru.vanek.task_management_application.repositories.CommentsRepository;
import ru.vanek.task_management_application.repositories.TasksRepository;
import ru.vanek.task_management_application.repositories.UsersRepository;
import ru.vanek.task_management_application.services.CommentsService;
import ru.vanek.task_management_application.utils.CommentConverter;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;
    private final TasksRepository tasksRepository;
    private final UsersRepository usersRepository;
    private final CommentConverter commentConverter;

    @Override
    public CommentResponse findOne(int id) {
        return commentConverter.convertToResponse(commentsRepository.findById(id).
                orElseThrow(()->new NoSuchElementException("Коментария с идентификатором  "+id+" - не существует")));
    }
    @Override
    @Transactional
    public CommentResponse publishComment(CommentRequest commentRequest, int taskId,String userEmail) {
        Comment comment = commentConverter.convertToComment(commentRequest);
        comment.setAuthor(usersRepository.findByEmail(userEmail).
                orElseThrow(()->new NoSuchElementException("Пользователя с emai'ом "+userEmail+" - не существует")));
        comment.setCreatedAt(new Date());
        comment.setCommentedTask(tasksRepository.findById(taskId).
                orElseThrow(()->new NoSuchElementException("Задачи с идентификатором "+taskId+" - не существует")));
        commentsRepository.save(comment);
        return commentConverter.convertToResponse(comment);
    }
    @Override
    @Transactional
    public void update(int commentId, CommentRequest commentRequest,String userEmail) {
        if(isEnoughRules(commentId,userEmail)){
            Comment updatedComment = commentsRepository.findById(commentId).
                    orElseThrow(()->new NoSuchElementException("Коментария с идентификатором "+commentId+" - не существует"));
            Comment changes = commentConverter.convertToComment(commentRequest);
            updatedComment.setText(changes.getText());
        }
    }
    @Override
    @Transactional
    public void delete(int commentId,String userEmail) {
        if(isEnoughRules(commentId,userEmail)){
            commentsRepository.deleteById(commentId);
        }

    }
    @Override
    public boolean isEnoughRules(int commentId, String email) {
        return email.equals(commentsRepository.findById(commentId)
                .orElseThrow(()->new NotEnoughRulesException("У вас недостаточно прав для выполнения данной операции")).getAuthor().getEmail());
    }
}
