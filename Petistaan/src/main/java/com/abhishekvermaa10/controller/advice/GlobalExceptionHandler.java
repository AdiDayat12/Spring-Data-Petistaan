package com.abhishekvermaa10.controller.advice;

import com.abhishekvermaa10.dto.ErrorDTO;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentException (MethodArgumentNotValidException e){
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        StringBuilder builder = new StringBuilder();
        for (String s : errors) {
            builder.append(s);
        }
        String errorMessage = builder.toString();
        return returnHandler(errorMessage, 400, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<ErrorDTO> ownerNotFound (OwnerNotFoundException e){
        return returnHandler(e.getMessage(), 404, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlePetNotFound (PetNotFoundException e){
        return returnHandler(e.getMessage(), 404, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleInvalidId (ConstraintViolationException e){
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(" "));
        return returnHandler(message, 400, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handlerInternalServerError (Exception e){
        return returnHandler(e.getMessage(), 400, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleMessageNotReadable (Exception e){
        String message = "";

        if (e.getCause() instanceof InvalidFormatException cause){
            List<JsonMappingException.Reference> path = cause.getPath();
            String fieldName = path.isEmpty() ? "unknown" : path.get(path.size() - 1).getFieldName();

            message = "Invalid value for field: " + fieldName + "(The valid value M or F)";
        }

        return returnHandler(message, 400, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorDTO> returnHandler (String message, int errorCode, HttpStatus httpStatus){
        return ResponseEntity.status(httpStatus).body(
                ErrorDTO.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(errorCode)
                        .message(message)
                        .error(httpStatus)
                        .build()
        );
    }

}
