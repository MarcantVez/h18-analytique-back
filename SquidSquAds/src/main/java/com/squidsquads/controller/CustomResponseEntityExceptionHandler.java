package com.squidsquads.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String path = ((ServletWebRequest) request).getNativeRequest(HttpServletRequest.class).getServletPath();
        return ResponseEntity.status(status).body(new ErrorMessage(path));
    }

    private class ErrorMessage {

        private String API_DOCS_URL = "https://squidsquads-backend-prod.herokuapp.com/swagger-ui.html";
        private String BAD_REQUEST_FOR_PATH = "Le corps de la requête est invalide. Veuillez consulter la documentation pour ";

        private String documentation = API_DOCS_URL;
        private String message;

        public ErrorMessage(String path) {
            this.message = BAD_REQUEST_FOR_PATH + path;
        }

        public String getDocumentation() {
            return documentation;
        }

        public String getMessage() {
            return message;
        }
    }
}
