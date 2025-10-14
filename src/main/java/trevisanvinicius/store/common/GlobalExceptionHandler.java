package trevisanvinicius.store.common;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import trevisanvinicius.store.payments.ErrorDTO;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException e
    ){
        var errors = new HashMap<String, String>();

        e.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorDTO> handleUnreadableMessage() {

        return ResponseEntity.badRequest().body(
                new ErrorDTO("Invalid request body"));
    }
}
