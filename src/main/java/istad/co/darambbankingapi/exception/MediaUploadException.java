package istad.co.darambbankingapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class MediaUploadException {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    ResponseEntity<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception){
        return null;
    }
}
