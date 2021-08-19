package com.example.CUSHProject.controller;

import com.example.CUSHProject.dto.MemberDto;
import com.example.CUSHProject.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@AllArgsConstructor
public class OverlapController {

    private final MemberService memberService;

    //회원가입 이메일 중복 체크
    @ResponseBody
    @RequestMapping(value = "/usernameOverlap", method = RequestMethod.POST)
    public HashMap<String, Object> usernameOverlap(@RequestParam(required = false) String username) {
        System.out.println("hello");
        return memberService.usernameOverlap(username);
    }

    //회원가입 닉네임 중복 체크
    @ResponseBody
    @RequestMapping(value = "/nicknameOverlap", method = RequestMethod.POST)
    public HashMap<String, Object> nicknameOverlap(@RequestParam(required = false) String nickname) {
        System.out.println("hello");
        return memberService.nicknameOverlap(nickname);
    }

    //아이디 수정 중복 체크
    @ResponseBody
    @RequestMapping(value = "/usernameModify", method = RequestMethod.POST)
    public HashMap<String, Object> usernameModify(@RequestParam(required = false) Long id, String username) {
        return memberService.usernameModify(username, id);
    }

    //닉네임 수정 중복 체크
    @ResponseBody
    @RequestMapping(value = "/nicknameModify", method = RequestMethod.POST)
    public HashMap<String, Object> nicknameModify(@RequestParam(required = false) Long id, String nickname) {
        return memberService.nicknameModify(nickname, id);
    }
}
