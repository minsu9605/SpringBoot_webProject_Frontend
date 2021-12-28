package com.example.CUSHProjectFront.controller;


import com.example.CUSHProjectFront.dto.BoardDto;
import com.example.CUSHProjectFront.handler.BoardRestApiHandler;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardRestApiHandler boardRestApiHandler;
    /*일반 게시판*/
    @GetMapping("/board/list")
    public String boardList(Model model) {
        model.addAttribute("categoryList", boardRestApiHandler.getCategoryList());
        System.out.println(boardRestApiHandler.getCategoryList());
        return "board/boardlist";
    }

    //글쓰기
    @GetMapping("/board/write")
    public String boardWrite(Model model) {
        BoardDto boardForm = BoardDto.builder().build();
        model.addAttribute("boardForm", boardForm);
        model.addAttribute("categoryList", boardRestApiHandler.getCategoryList());
        return "board/boardform";
    }

    @GetMapping("/board/content")
    public String boardContent(Model model, @RequestParam(required = false) Long id) throws Exception {
        boardRestApiHandler.boardHitUpdate(id);
        BoardDto boardForm = boardRestApiHandler.boardContent(id);

        model.addAttribute("categoryList", boardRestApiHandler.getCategoryList());
        model.addAttribute("boardForm", boardForm);
        return "board/boardcontent";
    }

    @GetMapping("/board/modify")
    public String boardModify(Model model, @RequestParam(required = false) Long id) throws Exception {
        BoardDto boardForm = boardRestApiHandler.boardContent(id);

        model.addAttribute("boardForm", boardForm);
        model.addAttribute("categoryList", boardRestApiHandler.getCategoryList());
        return "board/boardmodify";
    }

    @GetMapping("/board/map")
    public String showMap() {
        return "board/map";
    }

    @GetMapping("/board/map_content")
    public String showMapContent() {
        return "board/map_content";
    }

    //내가 쓴 게시물(내정보)
    @GetMapping("/board/myBoard")
    public String myBoard() {
        return "account/myboard";
    }

    //내가 쓴 오래된 게시물
    @GetMapping("/board/myOldBoard")
    public String myOldBoard() {
        return "account/myOldBoard";
    }

    /*내가 쓴 오래된 게시물 api*//*
    @GetMapping("/api/board/myOldBoard/table")
    @ResponseBody
    public HashMap<String, Object> getMyOldBoardList(@RequestParam(required = false) int page,
                                                     @RequestParam(required = false) int perPage,
                                                     @RequestParam(required = false) String searchType,
                                                     @RequestParam(required = false, defaultValue = "") String keyword, Authentication authentication) {
        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();

        int total = boardService.getMyOldBoardTotalSize(authentication.getName(), searchType, keyword);
        List<BoardDto> boardEntityList = boardService.getMyOldBoardList(authentication.getName(), page, perPage, searchType, keyword);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", boardEntityList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", page);
        paginationMap.put("totalCount", total);
        return objectMap;
    }

    @GetMapping("/api/board/getMyOldBoardList")
    @ResponseBody
    public HashMap<String, Object> oldBoardAlertList(Authentication authentication,
                                                @RequestParam(required = false) int startIndex,
                                                @RequestParam(required = false) int searchStep) {
        HashMap<String, Object> map = new HashMap<>();
        List<BoardDto> boardDtoList = boardService.getMyOldBoardAlertList(authentication.getName(), startIndex, searchStep);
        map.put("totalCnt", boardService.getMyOldBoardAlertListCnt(authentication.getName()));
        map.put("data", boardDtoList);
        return map;
    }

    @GetMapping("/api/board/getMyOldBoardCnt")
    @ResponseBody
    public HashMap<String, Object> getMyOldBoardAlertCnt(Authentication authentication) {
        HashMap<String, Object> map = new HashMap<>();

        if (authentication != null) {
            map.put("totalCnt", boardService.getMyOldBoardAlertListCnt(authentication.getName()));
        } else map.put("totalCnt", 0);

        return map;
    }

    @GetMapping("/api/board/setAlertReading")
    @ResponseBody
    public HashMap<String, Object> setAlertReading(@RequestParam(required = false) Long id) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("result",boardService.setAlertReading(id));

        return map;
    }

    *//*내가 쓴 게시물 api*//*
    @GetMapping("/api/board/myBoard/table")
    @ResponseBody
    public HashMap<String, Object> getMyBoardList(@RequestParam(required = false) int page,
                                                 @RequestParam(required = false) int perPage,
                                                 @RequestParam(required = false) String searchType,
                                                 @RequestParam(required = false, defaultValue = "") String keyword, Authentication authentication) {
        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();

        int total = boardService.getMyBoardTotalSize(authentication.getName(), searchType, keyword);
        List<BoardDto> boardEntityList = boardService.getMyBoardList(authentication.getName(), page, perPage, searchType, keyword);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", boardEntityList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", page);
        paginationMap.put("totalCount", total);
        return objectMap;
    }

    @ResponseBody
    @GetMapping("/api/admin/adminBoardChart")
    public HashMap<String, Object> adminBoardCnt(@RequestParam(required = false) String monthOption,
                                                 @RequestParam(required = false) String yearOption) {
        return boardService.getBoardCount(yearOption, monthOption);
    }*/

}
