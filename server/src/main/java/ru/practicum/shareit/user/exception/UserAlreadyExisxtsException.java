package ru.practicum.shareit.user.exception;

public class UserAlreadyExisxtsException extends RuntimeException {
    public UserAlreadyExisxtsException(String message) {
        super(message);
    }
}
