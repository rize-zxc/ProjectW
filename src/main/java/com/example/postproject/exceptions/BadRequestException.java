package com.example.postproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Аннотация указывает, что при выбросе этого исключения вернётся HTTP-статус 400
@SuppressWarnings("checkstyle:MissingJavadocType")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
  @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
  public BadRequestException(String message) {
        super(message);
    }
}