package istad.co.darambbankingapi.exception;

import istad.co.darambbankingapi.base.BasedError;
import istad.co.darambbankingapi.base.BasedErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class MediaUploadException {

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxSize;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    ResponseEntity<BasedErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception){
        BasedError<String> basedError = BasedError.<String>builder()
                .code(HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase())
                .description("Media upload size maximum is" + maxSize)
                .build();
        return new ResponseEntity<>(new BasedErrorResponse(basedError), HttpStatus.PAYLOAD_TOO_LARGE);
    }
}
