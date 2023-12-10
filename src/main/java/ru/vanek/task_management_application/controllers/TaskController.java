package ru.vanek.task_management_application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import ru.vanek.task_management_application.dtos.responses.ExceptionResponse;
import ru.vanek.task_management_application.dtos.responses.TaskResponse;
import ru.vanek.task_management_application.dtos.responses.ValidationErrorResponse;
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
    @Operation(summary = "Получить все задачи",
            description = "Получить все задачи",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            },tags = "task"

    )
    @GetMapping()
    public ResponseEntity<List<TaskResponse>> getAllTasks(@RequestParam(value = "page",required = false, defaultValue = "0" ) int page){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(taskService.findAll(page));
    }
    @Operation(summary = "Получить задачи исполнителя",
            description = "Получить все задачи по идентификатору исполнителя",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неверный идентификатор", responseCode = "405",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            },tags = "task"
    )
    @GetMapping("/executor/{executorId}")
    public ResponseEntity<List<TaskResponse>> getTasksByExecutorId(@PathVariable("executorId") int executorId){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(taskService.findAllByExecutorId(executorId));
    }
    @Operation(summary = "Получить задачи автора",
            description = "Получить все задачи по идентификатору автора",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неверный идентификатор", responseCode = "405",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            },tags = "task"
    )
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<TaskResponse>> getTasksByAuthorId(@PathVariable("authorId") int authorId){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(taskService.findAllByAuthorId(authorId));
    }
    @Operation(summary = "Получить все задачи",
            description = "Получить все задачи по идентификатору задачи",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неверный идентификатор", responseCode = "405",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            },tags = "task"
    )
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") int id){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(taskService.findOne(id));
    }
    @Operation(summary = "Создать задачу",
            description = "Создать задачу",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Не авторизован", responseCode = "401",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "409",
                            content = @Content(
                                    schema = @Schema(implementation = ValidationErrorResponse.class))),
                    @ApiResponse(description = "Время  жизни токена вышло, авторизйтесь заново", responseCode = "407",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            },tags = "task"
    )
    @PostMapping()
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest taskRequest, UriComponentsBuilder uriComponentsBuilder, Principal user) {
        TaskResponse taskResponse= taskService.create(taskRequest,user.getName());
        return ResponseEntity.created(uriComponentsBuilder
                        .path("/api/tasks")
                        .build(Map.of("taskId", taskResponse.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskResponse);
    }
    @Operation(summary = "Изменить статус задачи",
            description = "Изменить статус задачи по идентификатору",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Не авторизован", responseCode = "401",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неверный идентификатор", responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "409",
                            content = @Content(
                                    schema = @Schema(implementation = ValidationErrorResponse.class))),
                    @ApiResponse(description = "Время  жизни токена вышло, авторизйтесь заново", responseCode = "407",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            },tags = "task"
    )
    @PutMapping("/{id}/changeStatus")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TaskResponse> changeTaskStatus(@PathVariable int id, @Valid @RequestBody StatusChangeRequest statusChangeRequest, Principal user){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(taskService.changeTaskStatus(id,statusChangeRequest.getStatus(),user.getName()));
    }
    @Operation(summary = "Изменить задачу",
            description = "Изменить задачу по идентификатору",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Не авторизован", responseCode = "401",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неверный идентификатор", responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Отказано в доступе", responseCode = "405",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "409",
                            content = @Content(
                                    schema = @Schema(implementation = ValidationErrorResponse.class))),
                    @ApiResponse(description = "Время  жизни токена вышло, авторизйтесь заново", responseCode = "407",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))
    },tags = "task"
    )
    @PutMapping("/{id}/update")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TaskResponse> changeTask(@PathVariable int id,@Valid @RequestBody TaskRequest taskRequest, Principal user){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(taskService.update(id,taskRequest,user.getName()));
    }
    @Operation(summary = "Удалить задачу",
            description = "Удалить задачу по идентификатору",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Не авторизован", responseCode = "401",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неверный идентификатор", responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Отказано в доступе", responseCode = "405",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Время  жизни токена вышло, авторизйтесь заново", responseCode = "407",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            },tags = "task"
    )
    @DeleteMapping("/{id}/delete")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable int id, Principal user){
        taskService.delete(id,user.getName());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(HttpStatus.OK);
    }
    @Operation(summary = "Получить все коментарии к задаче",
            description = "Получить все коментарии к задаче по идентификатору",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неверный идентификатор", responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Отказано в доступе", responseCode = "405",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            },tags = "comment"
    )
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable int id){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(taskService.getAllTaskCommentsByTaskId(id));
    }
    @Operation(summary = "Опубликовать комментарий",
            description = "Опубликовать комментарий к задаче",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неверный идентификатор", responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "409",
                            content = @Content(
                                    schema = @Schema(implementation = ValidationErrorResponse.class))),
                    @ApiResponse(description = "Время  жизни токена вышло, авторизйтесь заново", responseCode = "407",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            },tags = "comment"
    )
    @PostMapping("/{id}/comments")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CommentResponse> publishComment(@Valid @RequestBody CommentRequest commentRequest, UriComponentsBuilder uriComponentsBuilder, @PathVariable int id, Principal user) {
        CommentResponse commentResponse= commentsService.publishComment(commentRequest,id,user.getName());
        return ResponseEntity.created(uriComponentsBuilder
                        .path("/api/comments")
                        .build(Map.of("commentId", commentResponse.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentResponse);
    }
    @Operation(summary = "Получить комментарий",
            description = "Получить комментарий по идентификатору",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неверный идентификатор", responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Не авторизован", responseCode = "401",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Отказано в доступе", responseCode = "405",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            },tags = "comment"
    )
    @GetMapping("/{id}/comments/{commentId}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable("id") int id,@PathVariable("commentId") int commentId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(commentsService.findOne(commentId));
    }
    @Operation(summary = "Редактировать комментарий",
            description = "Редактировать комментарий",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неверный идентификатор", responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Не авторизован", responseCode = "401",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Отказано в доступе", responseCode = "405",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "409",
                            content = @Content(
                                    schema = @Schema(implementation = ValidationErrorResponse.class))),
                    @ApiResponse(description = "Время  жизни токена вышло, авторизйтесь заново", responseCode = "407",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            },tags = "comment"
    )
    @PutMapping("/{id}/comments/{commentId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<HttpStatus> changeComment(@PathVariable("id") int id,@Valid @RequestBody CommentRequest commentRequest,@PathVariable("commentId") int commentId,Principal user) {
        commentsService.update(commentId, commentRequest,user.getName());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(HttpStatus.OK);
    }
    @Operation(summary = "Удалить комментарий",
            description = "Удалить комментарий",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неверный идентификатор", responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Не авторизован", responseCode = "401",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Отказано в доступе", responseCode = "405",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Время  жизни токена вышло, авторизйтесь заново", responseCode = "407",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))

            },tags = "comment"
    )
    @DeleteMapping("/{id}/comments/{commentId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") int id,@PathVariable("commentId") int commentId,Principal user) {
        commentsService.delete(commentId,user.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}