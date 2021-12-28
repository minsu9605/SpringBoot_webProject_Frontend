package com.example.CUSHProjectFront.apicontroller;

import com.example.CUSHProjectFront.dto.BoardDto;
import com.example.CUSHProjectFront.dto.BoardListDto;
import com.example.CUSHProjectFront.dto.NoticeBoardDto;
import com.example.CUSHProjectFront.dto.NoticeBoardListDto;
import com.example.CUSHProjectFront.handler.BoardRestApiHandler;
import com.example.CUSHProjectFront.handler.NoticeBoardRestApiHandler;
import com.example.CUSHProjectFront.handler.RestTemplateHandler;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@AllArgsConstructor
public class NoticeBoardRestApiController {
    private final NoticeBoardRestApiHandler noticeBoardRestApiHandler;


    @GetMapping("/api/notice/list/table")
    public HashMap<String,Object> getNoticeBoardList(NoticeBoardListDto noticeBoardListDto) throws Exception{
        return noticeBoardRestApiHandler.getNoticeBoardList(noticeBoardListDto);
    }

    @PostMapping("/api/notice/write")
    public HashMap<String, Object> noticeBoardWrite(NoticeBoardDto noticeBoardDto) throws Exception{
        return noticeBoardRestApiHandler.noticeBoardWrite(noticeBoardDto);
    }

    @PostMapping("/api/notice/modify")
    public HashMap<String, Object> boardModify(NoticeBoardDto noticeBoardDto) throws Exception {
        return noticeBoardRestApiHandler.noticeBoardModify(noticeBoardDto);
    }

    @DeleteMapping("/api/notice/delete")
    public HashMap<String, Object> boardDelete(@RequestParam(required = false) Long id) throws Exception{
        return noticeBoardRestApiHandler.noticeBoardDelete(id);
    }

}
