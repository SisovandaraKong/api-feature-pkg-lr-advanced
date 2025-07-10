package istad.co.darambbankingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class HandleServiceException {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleServiceException(ResponseStatusException exception) {
        return new ResponseEntity<>(
                Map.of(
                        "message", "Error Business Logic",
                        "status", exception.getStatusCode().value(),
                        "timestamp", LocalDateTime.now(),
                        "details", exception.getReason()
                ), HttpStatus.BAD_REQUEST
        );
    }
}

