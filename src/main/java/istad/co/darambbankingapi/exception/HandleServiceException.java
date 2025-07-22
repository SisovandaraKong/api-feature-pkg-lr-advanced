package istad.co.darambbankingapi.exception;

import istad.co.darambbankingapi.base.BasedError;
import istad.co.darambbankingapi.base.BasedErrorResponse;
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
    public ResponseEntity<?> handleServiceException(ResponseStatusException exception){
        BasedError<String> basedError = new BasedError<>();
        basedError.setCode(exception.getStatusCode().toString());
        basedError.setDescription(exception.getReason());

        BasedErrorResponse basedErrorResponse = new BasedErrorResponse();
        basedErrorResponse.setError(basedError);
        return new ResponseEntity<>(basedErrorResponse,HttpStatus.BAD_REQUEST);
    }

}

