package ru.practicum.ewm.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.common.errors.*;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound (NotFoundException e) {
        ApiError apiError = ApiError.builder()
                .message(String.format("%s which has id %d not found", e.getEntityType(), e.getEntityId()))
                .status(HttpStatus.NOT_FOUND)
                .build();
        log.warn(apiError.getMessage());

        return handleAndResponse(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleIntegrityViolation(DataIntegrityViolationException e) {
        ApiError apiError = ApiError.builder()
                .message("Data integrity violation")
                .status(HttpStatus.CONFLICT)
                .build();
        if (e.getRootCause() != null) {
            apiError.setReason(e.getRootCause().getMessage());
        }

        return handleAndResponse(apiError);
    }

    private ResponseEntity<ApiError> handleAndResponse(ApiError apiError) {
        log.warn(apiError.getMessage());

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
