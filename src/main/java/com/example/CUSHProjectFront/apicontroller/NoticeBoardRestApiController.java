package com.example.CUSHProjectFront.apicontroller;

import com.example.CUSHProjectFront.dto.BoardListDto;
import com.example.CUSHProjectFront.dto.NoticeBoardListDto;
import com.example.CUSHProjectFront.handler.BoardRestApiHandler;
import com.example.CUSHProjectFront.handler.NoticeBoardRestApiHandler;
import com.example.CUSHProjectFront.handler.RestTemplateHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@AllArgsConstructor
public class NoticeBoardRestApiController {
    private final NoticeBoardRestApiHandler noticeBoardRestApiHandler;


    @GetMapping("/api/notice/list/table")
    public HashMap<String,Object> getNoticeBoardList(NoticeBoardListDto noticeBoardListDto) throws Exception{
        return noticeBoardRestApiHandler.getNoticeBoardList(noticeBoardListDto);
    }
}
