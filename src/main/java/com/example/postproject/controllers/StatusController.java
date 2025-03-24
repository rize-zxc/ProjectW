package com.example.postproject.controllers;

import com.example.postproject.services.StatusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/")
    public String index(Model model) {
        if (!statusService.isServerAvailable()) {
            model.addAttribute("message", "Сервис временно недоступен. Иди меняй статус.");
            return "error";
        }

        return "index";
    }

    @GetMapping("/status")
    @ResponseBody
    public Map<String, String> checkStatus(@RequestParam(name = "status", required = false) String status) {
        return statusService.updateAndGetStatus(status);
    }
}