package com.example.CUSHProjectFront.controller;

import com.example.CUSHProjectFront.dto.BoardDto;
import com.example.CUSHProjectFront.dto.NoticeBoardDto;
import com.example.CUSHProjectFront.handler.NoticeBoardRestApiHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class NoticeBoardController {

    /*private final NoticeBoardService noticeBoardService;*/
    private final NoticeBoardRestApiHandler noticeBoardRestApiHandler;
    /*공지사항*/
    @GetMapping("/notice/list")
    public String noticeList() {
        return "notice/noticelist";
    }

    @GetMapping("/notice/content")
    public String noticeBoardContent(Model model, @RequestParam(required = false) Long id) throws Exception{
        noticeBoardRestApiHandler.noticeBoardHitUpdate(id);
        NoticeBoardDto boardForm = noticeBoardRestApiHandler.noticeBoardContent(id);

        model.addAttribute("boardForm",boardForm);
        return "notice/noticecontent";
    }

    @GetMapping("/notice/write")
    public String noticeBoardWrite(Model model) {
        NoticeBoardDto boardForm = new NoticeBoardDto();

        model.addAttribute("boardForm",boardForm);
        return "notice/noticeform";
    }

    @GetMapping("/notice/modify")
    public String noticeBoardModify(Model model, @RequestParam(required = false) Long id) throws Exception{
        NoticeBoardDto boardForm = noticeBoardRestApiHandler.noticeBoardContent(id);
        model.addAttribute("boardForm",boardForm);
        return "notice/noticemodify";
    }
}
