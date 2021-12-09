package com.example.CUSHProject.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
