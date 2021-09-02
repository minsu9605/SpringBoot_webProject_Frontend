package com.example.CUSHProject.controller;

import com.example.CUSHProject.dto.MemberDto;
import com.example.CUSHProject.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
@AllArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(HttpSession session) {
        System.out.println("-----------------------------------");
        System.out.println("세션 닉네임 : "+session.getAttribute("memberNickname"));
        System.out.println("-----------------------------------");
        return "index";
    }
}
