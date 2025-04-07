package com.example.postproject.services;

import com.example.postproject.models.ServerStatus;
import com.example.postproject.singleton.ServerStatusSingleton;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;



@SuppressWarnings({"checkstyle:MissingJavadocType", "checkstyle:Indentation"})
@Service
public class StatusService {

  private final ServerStatus serverStatus;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public StatusService() {
    this.serverStatus = ServerStatusSingleton.getInstance(); // получаем синглтон
    }

  public boolean isServerAvailable() {
     return serverStatus.isAvailable();
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public Map<String, String> updateAndGetStatus(String status) {
        if (status != null) {
            if ("available".equalsIgnoreCase(status)) {
                serverStatus.setAvailable(true);
            } else if ("unavailable".equalsIgnoreCase(status)) {
                serverStatus.setAvailable(false);
            }
        }

        Map<String, String> response = new HashMap<>();
        if (serverStatus.isAvailable()) {
            response.put("status", "available");
            response.put("message", "Сервис работает в штатном режиме.");
        } else {
            response.put("status", "unavailable");
            response.put("message", "Сервис временно недоступен. Пожалуйста, попробуйте позже.");
        }

        return response;
    }
}
