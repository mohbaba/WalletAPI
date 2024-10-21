package com.example.api.infrastructure.adapter.output.customizedExceptions;

import com.example.api.domain.exceptions.UserExistsException;
import com.example.api.infrastructure.adapter.output.customizedExceptions.data.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@RestController
public class CustomizedExceptionsAdapter extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserExistsException.class)
    public final ResponseEntity<Object> handleUserExistsException(final UserExistsException userExistsException, final WebRequest request){
        final ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), userExistsException.getMessage(),
				List.of(request.getDescription(false)));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
