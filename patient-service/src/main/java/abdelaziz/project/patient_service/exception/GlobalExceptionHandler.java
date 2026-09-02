package abdelaziz.project.patient_service.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//a specialized annotation that allows you to handle exceptions globally across your entire application
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleException(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        
        logger.error("EmailAlreadyExistsException: " + ex.getMessage());
        
        Map<String,String> error = new HashMap<>();
        error.put("error", "Email Already exists");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String,String>> 
    handlePatientNotFoundException(PatientNotFoundException ex
) {
        //this is for logging the exception message to the console or a log file for debugging purposes
        logger.error("PatientNotFoundException: " + ex.getMessage());
        
        // this is for creating a response body that contains the error message to be sent back to the client
        Map<String,String> error = new HashMap<>();
        error.put("error", "Patient Not Found");
        return ResponseEntity.badRequest().body(error);
    }
}
