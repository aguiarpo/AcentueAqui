package com.pac.acentueaqui.handler;

import com.pac.acentueaqui.error.ErrorDetails;
import com.pac.acentueaqui.error.ErrorDetailsWithFieldMessage;
import com.pac.acentueaqui.error.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException){
        ErrorDetailsWithFieldMessage rfnExceptionDetails =  ErrorDetailsWithFieldMessage.Builder.newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource Not Found")
                .detail(rfnException.getMessage())
                .field("Erro de acesso")
                .fieldMessage(rfnException.getMessage())
                .developmentMessage(rfnException.getClass().getName())
                .build();
        return new ResponseEntity<>(rfnExceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException cvException){
        String constraintName;
        if(cvException.getConstraintName() == null){
            constraintName = "Desculpa mas nehuma informação pode ser nula";
        }else{
            constraintName = "A propriedade " + cvException.getConstraintName() + " já foi cadastrado(a)" ;
        }

        ErrorDetailsWithFieldMessage cvExceptionDetail =  ErrorDetailsWithFieldMessage.Builder.newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("ConstraintViolationException")
                .detail(cvException.getMessage())
                .developmentMessage(cvException.getClass().getName())
                .field("Erro ao se conectar ao Banco de Dados")
                .fieldMessage(constraintName)
                .build();
        return new ResponseEntity<>(cvExceptionDetail, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<?> handlePropertyReferenceDetails(PropertyReferenceException prException){

        String fieldMessage = "A propriedade '" + prException.getPropertyName() + "' não existe ";
        ErrorDetailsWithFieldMessage prExceptionDetail =  ErrorDetailsWithFieldMessage.Builder.newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("PropertyReferenceDetails")
                .detail(prException.getMessage())
                .developmentMessage(prException.getClass().getName())
                .field("Erro ao se conectar no Banco de Dados")
                .fieldMessage(fieldMessage)
                .build();
        return new ResponseEntity<>(prExceptionDetail, HttpStatus.NOT_FOUND);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        ErrorDetails errorDetails =  ErrorDetails.Builder.newBuilder()
                .timestamp(new Date().getTime())
                .status(status.value())
                .title("Internal Exception")
                .detail(ex.getMessage())
                .developmentMessage(ex.getClass().getName())
                .build();
        return new ResponseEntity<>(errorDetails, headers, status);
    }

}