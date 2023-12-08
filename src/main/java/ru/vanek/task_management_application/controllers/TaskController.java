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
    @GetMapping("/executor/{executorId}")
    public ResponseEntity<List<TaskResponse>> getTasksByExecutorId(@PathVariable("executorId") int executorId){
        return ResponseEntity.ok(taskService.findAllByExecutorId(executorId));
    }
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<TaskResponse>> getTasksByAuthorId(@PathVariable("authorId") int authorId){
        return ResponseEntity.ok(taskService.findAllByAuthorId(authorId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") int id){
        return ResponseEntity.ok(taskService.findOne(id));
    }
    @PostMapping()
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest taskRequest, UriComponentsBuilder uriComponentsBuilder, Principal user) {
        TaskResponse taskResponse= taskService.create(taskRequest,user.getName());
        return ResponseEntity.created(uriComponentsBuilder
                        .path("/api/tasks")
                        .build(Map.of("taskId", taskResponse.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskResponse);
    }
    @PutMapping("/{id}/changeStatus")
    public ResponseEntity<TaskResponse> changeTaskStatus(@PathVariable int id, @RequestBody StatusChangeRequest statusChangeRequest, Principal user){
        return ResponseEntity.ok(taskService.changeTaskStatus(id,statusChangeRequest.getStatus(),user.getName()));
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<TaskResponse> changeTask(@PathVariable int id, @RequestBody TaskRequest taskRequest, Principal user){
        return ResponseEntity.ok(taskService.update(id,taskRequest,user.getName()));
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable int id, Principal user){
        taskService.delete(id,user.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable int id){
        return ResponseEntity.ok(taskService.getAllTaskCommentsByTaskId(id));
    }
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponse> publishComment( @RequestBody CommentRequest commentRequest, UriComponentsBuilder uriComponentsBuilder, @PathVariable int id, Principal user) {
        CommentResponse commentResponse= commentsService.publishComment(commentRequest,id,user.getName());
        return ResponseEntity.created(uriComponentsBuilder
                        .path("/api/comments")
                        .build(Map.of("commentId", commentResponse.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentResponse);
    }
    @GetMapping("/{id}/comments/{commentId}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable("id") int id,@PathVariable("commentId") int commentId) {
        return ResponseEntity.ok(commentsService.findOne(commentId));
    }

    @PutMapping("/{id}/comments/{commentId}")
    public ResponseEntity<HttpStatus> changeComment(@PathVariable("id") int id, @RequestBody CommentRequest commentRequest,@PathVariable("commentId") int commentId,Principal user) {
        //todo переделать principal
        commentsService.update(commentId, commentRequest,user.getName());
        return ResponseEntity.ok(HttpStatus.OK);//todo сделать так чтобы возвращался нормыльный статус updated
    }
    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") int id,@PathVariable("commentId") int commentId,Principal user) {
        commentsService.delete(commentId,user.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
