package ru.vanek.task_management_application.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.vanek.task_management_application.dtos.requests.CommentRequest;
import ru.vanek.task_management_application.dtos.requests.StatusChangeRequest;
import ru.vanek.task_management_application.dtos.requests.TaskRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;
import ru.vanek.task_management_application.dtos.responses.TaskResponse;
import ru.vanek.task_management_application.services.CommentsService;
import ru.vanek.task_management_application.services.TaskService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final CommentsService commentsService;
    @Autowired
    public TaskController(TaskService taskService, CommentsService commentsService) {
        this.taskService = taskService;
        this.commentsService = commentsService;
    }

    @GetMapping()
    public ResponseEntity<List<TaskResponse>> getAllTasks(@RequestParam(value = "page",required = false, defaultValue = "0" ) int page){
        return ResponseEntity.ok(taskService.findAll(page));
    }
    @GetMapping("/executor/{executorName}")
    public ResponseEntity<List<TaskResponse>> getTasksByExecutorName(@PathVariable("executorName") String executorName){
        return ResponseEntity.ok(taskService.findAllByExecutorName(executorName));
    }
    @GetMapping("/author/{authorName}")
    public ResponseEntity<List<TaskResponse>> getTasksByAuthorName(@PathVariable("authorName") String authorName){
        return ResponseEntity.ok(taskService.findAllByAuthorName(authorName));
    }
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTasksById(@PathVariable("id") int id){
        return ResponseEntity.ok(taskService.findOne(id));
    }
    @PutMapping("/{id}/changeStatus")
    public ResponseEntity<TaskResponse> changeTaskStatus(@PathVariable int id, @RequestBody StatusChangeRequest statusChangeRequest){
        return ResponseEntity.ok(taskService.changeTaskStatus(id,statusChangeRequest.getStatus()));
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<TaskResponse> changeTask(@PathVariable int id, @RequestBody TaskRequest taskRequest){
        return ResponseEntity.ok(taskService.update(id,taskRequest));
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable int id){
        taskService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable int id){
        return ResponseEntity.ok(taskService.getAllTaskCommentsByTaskId(id));
    }
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponse> publishComment(@Valid @RequestBody CommentRequest commentRequest, UriComponentsBuilder uriComponentsBuilder, @PathVariable int id) {
        CommentResponse commentResponse= commentsService.publishComment(commentRequest,id);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("/api/comments/{commentId}")
                        .build(Map.of("commentId", commentResponse.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentResponse);
    }
    @GetMapping("/{id}/comments/{commentId}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable("id") int id,@PathVariable("commentId") int commentId) {
        return ResponseEntity.ok(commentsService.findOne(commentId));
    }

    @PutMapping("/{id}/comments/{commentId}")
    public ResponseEntity<HttpStatus> changeComment(@PathVariable("id") int id, Principal principal, @RequestBody CommentRequest commentRequest,@PathVariable("commentId") int commentId) {
        //todo переделать principal
        commentsService.update(id, commentRequest);
        return ResponseEntity.ok(HttpStatus.OK);//todo сделать так чтобы возвращался нормыльный статус updated
    }
    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") int id, Principal principal,@PathVariable("commentId") int commentId) {
        commentsService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
