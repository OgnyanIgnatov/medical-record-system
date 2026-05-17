package com.example.medical_record_system.web.view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @GetMapping("/")
    public String getIndex(Model model){
        final String welcomeMessage = "Welcome!";
        model.addAttribute("welcome", welcomeMessage);
        return "index";

    }
}
