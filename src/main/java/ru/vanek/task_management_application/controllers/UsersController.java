package ru.vanek.task_management_application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.vanek.task_management_application.dtos.requests.JwtRequest;
import ru.vanek.task_management_application.dtos.requests.UserRequest;
import ru.vanek.task_management_application.dtos.responses.*;
import ru.vanek.task_management_application.services.AuthService;
import ru.vanek.task_management_application.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User")
public class UsersController {
    private final UserService userService;
    private final AuthService authService;
    public UsersController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }
    @Operation(summary = "Авторизоваться",
            description = "Получить токен авторизации",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200"),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
            }
    )
    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> createAuthToken(@Valid @RequestBody JwtRequest authRequest){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(authService.createAuthToken(authRequest));
    }
    @Operation(summary = "Зарегистрироваться",
            description = "Зарегистрироваться",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200"),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "409",
                            content = @Content(
                                    schema = @Schema(implementation = ValidationErrorResponse.class)))
            }
    )
    @PostMapping("/register")
    public ResponseEntity<UserResponse> createNewUser(@Valid @RequestBody UserRequest userRequest, UriComponentsBuilder uriComponentsBuilder) {
        UserResponse userResponse= authService.createNewUser(userRequest);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("/api/users")
                        .build(Map.of("userId", userResponse.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(userResponse);
    }
    @Operation(summary = "Получить всех пользователей",
            description = "Получить всех пользователей",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200"),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "409",
                            content = @Content(
                                    schema = @Schema(implementation = ValidationErrorResponse.class)))
            }
    )
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getUsers(@RequestParam(value = "page",required = false, defaultValue = "0" ) int page) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.findAll(page));
    }
    @Operation(summary = "Получить пользователя",
            description = "Получить пользователя по идентификатору",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200"),
                    @ApiResponse(description = "Неизвестная ошибка", responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> show(@PathVariable("id") int id) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.findOne(id));
    }
    @Operation(summary = "Изменить пользователя",
            description = "Изменить пользователя по идентификатору",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200"),
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
            }
    )
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<HttpStatus> edit(@PathVariable("id") int id,  Principal user, @Valid @RequestBody UserRequest userRequest) {
        userService.update(id,userRequest,user.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @Operation(summary = "Удалить пользователя",
            description = "Удалить пользователя по идентификатору",
            responses = {
                    @ApiResponse(description = "Успешно", responseCode = "200"),
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
            }
    )
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id,Principal user) {
        userService.delete(id,user.getName());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(HttpStatus.OK);
    }
}

