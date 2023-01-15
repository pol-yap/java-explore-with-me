package ru.practicum.ewm.common.errors;

public class ForbiddenException  extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
