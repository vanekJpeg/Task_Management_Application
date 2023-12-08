package ru.vanek.task_management_application.exceptions;

public class WrongIdException extends RuntimeException{
    public WrongIdException(String message) {
        super(message);
    }
}
