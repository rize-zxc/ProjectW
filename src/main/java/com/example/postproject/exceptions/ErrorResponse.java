package com.example.postproject.exceptions;

@SuppressWarnings({"checkstyle:MissingJavadocType", "checkstyle:Indentation"})
public class ErrorResponse {
    @SuppressWarnings("checkstyle:Indentation")
  private String message;
  private String details;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public ErrorResponse(String message, String details) {
    this.message = message;
    this.details = details;
    }
  @SuppressWarnings("checkstyle:EmptyLineSeparator")
  public String getMessage() {
        return message;
    }

  public String getDetails() {
        return details;
    }
}