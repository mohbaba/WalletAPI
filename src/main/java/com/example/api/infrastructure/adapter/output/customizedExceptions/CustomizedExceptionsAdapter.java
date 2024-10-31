package com.example.api.infrastructure.adapter.output.customizedExceptions;

import com.example.api.domain.exceptions.*;
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

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(final UserNotFoundException userNotFoundException, final WebRequest request){
        final ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), userNotFoundException.getMessage(),
                List.of(request.getDescription(false)));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public final ResponseEntity<Object> handleWalletNotFoundException(final WalletNotFoundException walletNotFoundException, final WebRequest request){
        final ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), walletNotFoundException.getMessage(),
                List.of(request.getDescription(false)));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public final ResponseEntity<Object> handlePasswordMismatchException(final PasswordMismatchException passwordMismatchException, final WebRequest request){
        final ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), passwordMismatchException.getMessage(),
                List.of(request.getDescription(false)));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConnectionErrorException.class)
    public final ResponseEntity<Object> handleConnectionErrorException(final ConnectionErrorException connectionErrorException, final WebRequest request){
        final ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), connectionErrorException.getMessage(),
                List.of(request.getDescription(false)));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(MissingFieldException.class)
    public final ResponseEntity<Object> handleMissingFieldException(final MissingFieldException missingFieldException, final WebRequest request){
        final ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), missingFieldException.getMessage(),
                List.of(request.getDescription(false)));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnProcessedRequestException.class)
    public final ResponseEntity<Object> handleUnProcessedRequestException(final UnProcessedRequestException unProcessedRequestException, final WebRequest request){
        final ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), unProcessedRequestException.getMessage(),
                List.of(request.getDescription(false)));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public final ResponseEntity<Object> handleTransactionNotFound(final TransactionNotFoundException transactionNotFoundException){
        final ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), transactionNotFoundException.getMessage(),
                List.of("Transaction not found"));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
