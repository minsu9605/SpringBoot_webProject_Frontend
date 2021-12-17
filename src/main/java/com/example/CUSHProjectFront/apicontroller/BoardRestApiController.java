package com.example.CUSHProjectFront.apicontroller;

import com.example.CUSHProjectFront.dto.BoardListDto;
import com.example.CUSHProjectFront.handler.BoardRestApiHandler;
import com.example.CUSHProjectFront.handler.RestTemplateHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class BoardRestApiController {
    private final RestTemplateHandler restTemplateHandler;
    private final BoardRestApiHandler boardRestApiHandler;


    /*@GetMapping("/api/test/hello")
    public String testApi(Model model) throws Exception{
       return restTemplateHandler.restTemplateTest()
    }*/

    @GetMapping("/api/board/getMyOldBoardCnt")
    public HashMap<String,Object> getMyOldBoardCnt() throws Exception{
        return restTemplateHandler.OldBoardCount();
    }

    @GetMapping("/api/board/list/table")
    public HashMap<String,Object> getBoardList(BoardListDto boardListDto) throws Exception{
        return boardRestApiHandler.getBoardList(boardListDto);
    }

}
