package com.example.postproject.controllers;

import com.example.postproject.services.StatusService;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@SuppressWarnings("checkstyle:MissingJavadocType")
@Controller
public class StatusController {

    @SuppressWarnings("checkstyle:Indentation")
    private final StatusService statusService;

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
    @GetMapping("/")
    public String index(Model model) {
        if (!statusService.isServerAvailable()) {
            model.addAttribute("message", "Сервис временно недоступен. Иди меняй статус.");
            return "error";
        }

        return "index";
    }

    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:MissingJavadocMethod"})
    @GetMapping("/status")
    @ResponseBody
    public Map<String, String> checkStatus(@RequestParam(name = "status", required = false) String status) {
        return statusService.updateAndGetStatus(status);
    }
}