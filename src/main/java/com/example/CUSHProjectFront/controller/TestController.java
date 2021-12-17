package com.example.CUSHProjectFront.controller;

import com.example.CUSHProjectFront.handler.RestTemplateHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class TestController {
    private final RestTemplateHandler restTemplateHandler;

    @GetMapping("/api/test")
    public String view() {
        return "test/aaa";
    }

    @GetMapping("/api/test/hello")
    public String testApi(Model model) throws Exception{
        model.addAttribute("modelTest", restTemplateHandler.restTemplateTest());
        return "test/bbb";
    }

}
