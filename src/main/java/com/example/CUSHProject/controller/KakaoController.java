package com.example.CUSHProject.controller;


import com.example.CUSHProject.dto.OAuthToken;
import com.example.CUSHProject.repository.MemberRepository;
import com.example.CUSHProject.service.KakaoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class KakaoController {
    @Autowired
    private KakaoService kakaoService;

    @GetMapping(value="/kakao/oauth")
    public String kakaoConnect() {

        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=f795106be2877118844d807a1e9b8cc9");
        url.append("&redirect_uri=http://localhost:8080/kakao/callback");
        url.append("&response_type=code");

        return "redirect:" + url;
    }

        @GetMapping(value = "/kakao/callback")
        public String kakaoOauthRedirect(@RequestParam String code,OAuthToken oauthToken, MemberRepository memberRepository) throws Exception {
            OAuthToken oAuthToken = kakaoService.getAccessToken(code);
//            return kakaoService.getKakaoProfile(oAuthToken,memberRepository);
            return "index";
        }
}