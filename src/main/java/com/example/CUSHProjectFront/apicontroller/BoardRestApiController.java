package com.example.CUSHProjectFront.apicontroller;

import com.example.CUSHProjectFront.dto.BoardDto;
import com.example.CUSHProjectFront.dto.BoardListDto;
import com.example.CUSHProjectFront.handler.BoardRestApiHandler;
import com.example.CUSHProjectFront.handler.RestTemplateHandler;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class BoardRestApiController {
    private final RestTemplateHandler restTemplateHandler;
    private final BoardRestApiHandler boardRestApiHandler;


    @GetMapping("/api/board/soldOut")
    public String boardMap(@RequestParam(required = false) Long id) throws Exception {
        boardRestApiHandler.setSoldOut(id);
        return "success";
    }

    @GetMapping("/api/board/list/table")
    public HashMap<String, Object> getBoardList(BoardListDto boardListDto) throws Exception {
        return boardRestApiHandler.getBoardList(boardListDto);
    }

    @GetMapping("/api/board/getMyOldBoardCnt")
    public HashMap<String, Object> getMyOldBoardCnt(Authentication authentication) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        if (authentication != null) {
            map.put("totalCnt", boardRestApiHandler.getMyOldBoardAlertCnt(authentication.getName()));
        } else {
            map.put("totalCnt", 0);
        }
        return map;
    }

    @GetMapping("/api/board/getMyOldBoardList")
    public HashMap<String, Object> oldBoardAlertList(Authentication authentication,
                                                     @RequestParam(required = false) int startIndex,
                                                     @RequestParam(required = false) int searchStep) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        List<BoardDto> boardDtoList = boardRestApiHandler.getMyOldBoardAlertList(authentication.getName(), startIndex, searchStep);
        map.put("totalCnt", boardRestApiHandler.getMyOldBoardAlertCnt(authentication.getName()));
        map.put("data", boardDtoList);
        return map;
    }

    @PostMapping("/api/board/write")
    public HashMap<String, Object> boardWrite(BoardDto boardDto) throws Exception {
        return boardRestApiHandler.boardWrite(boardDto);
    }

    @PostMapping("/api/board/modify")
    public HashMap<String, Object> boardModify(BoardDto boardDto) throws Exception {
        return boardRestApiHandler.boardModify(boardDto);
    }

    @DeleteMapping("/api/board/delete")
    public HashMap<String, Object> boardDelete(@RequestParam(required = false) Long id) throws Exception{
        return boardRestApiHandler.boardDelete(id);
    }

}
