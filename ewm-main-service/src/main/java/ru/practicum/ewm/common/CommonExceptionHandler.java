package ru.practicum.ewm.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.common.errors.*;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException e) {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String reason = e.getBindingResult()
                         .getAllErrors()
                         .stream()
                         .map(DefaultMessageSourceResolvable::getDefaultMessage)
                         .collect(Collectors.toList())
                         .toString();

        ApiError apiError = ApiError.builder()
                                    .message("Data validation violation")
                                    .reason(reason)
                                    .status(HttpStatus.BAD_REQUEST)
                                    .build();

        return handleAndResponse(apiError);
    }

    @ExceptionHandler(DataCheckException.class)
    public ResponseEntity<ApiError> handleDataCheckException(DataCheckException e) {
        ApiError apiError = ApiError.builder()
                                    .message(e.getMessage())
                                    .status(HttpStatus.BAD_REQUEST)
                                    .build();

        return handleAndResponse(apiError);
    }

    private ResponseEntity<ApiError> handleAndResponse(ApiError apiError) {
        log.warn(apiError.getMessage());

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
