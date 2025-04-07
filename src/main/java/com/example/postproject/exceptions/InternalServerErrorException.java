package com.example.postproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("checkstyle:MissingJavadocType")
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {
  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public InternalServerErrorException(String message) {
    super(message);
    }
}