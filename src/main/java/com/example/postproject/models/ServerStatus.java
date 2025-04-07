package com.example.postproject.models;

@SuppressWarnings({"checkstyle:MissingJavadocType", "checkstyle:Indentation"})
public class ServerStatus {
  private boolean available;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public ServerStatus() {
        this.available = true;
    }

  public boolean isAvailable() {
        return available;
    }

  public void setAvailable(boolean available) {
        this.available = available;
    }
}