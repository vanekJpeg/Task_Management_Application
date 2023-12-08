package ru.vanek.task_management_application.exceptions;

public class PasteNotFoundException extends RuntimeException{
    public PasteNotFoundException(String message) {
        super(message);
    }
}
