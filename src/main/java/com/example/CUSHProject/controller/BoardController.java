package com.example.CUSHProject.controller;

import com.example.CUSHProject.Pagination.Paging;
import com.example.CUSHProject.dto.BoardDto;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.service.BoardService;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @RequestParam(value ="page", defaultValue = "1") Integer curPageNum,
                            @RequestParam(defaultValue = "title") String searchType,
                            @RequestParam(required = false, defaultValue = "")String keyword) {
        Paging paging = new Paging();
        Page<BoardEntity> boardList = boardService.boardList(searchType, keyword, curPageNum);
        double listCnt = boardService.getBoardListCnt(keyword);
        paging.pageInfo(curPageNum, listCnt);
        model.addAttribute("boardList",boardList);
        model.addAttribute("paging",paging);
        return "board/boardlist";
    }

    //글쓰기
    @GetMapping("/board/write")
    public String boardWrite(Model model) {
        BoardDto boardForm = boardService.boardFormWrite();
        model.addAttribute("boardForm",boardForm);
        return "board/boardform";
    }

    @PostMapping("/board/write")
    public String boardWrite(BoardDto boardDto){
        boardService.boardWrite(boardDto);
        return "redirect:/board/list";
    }

    @GetMapping("/board/content")
    public String boardContent(Model model, @RequestParam(required = false) Long id){
        BoardDto boardForm = boardService.boardContent(id);
        model.addAttribute("boardForm",boardForm);
        System.out.println("-------------------------");
        System.out.println("수정날짜"+boardForm.getUpdatedDate());
        System.out.println("-------------------------");
        return "board/boardcontent";
    }

    @GetMapping("/board/modify")
    public String boardModify(Model model, @RequestParam(required = false) Long id){
        BoardDto boardForm = boardService.boardContent(id);
        model.addAttribute("boardForm",boardForm);
        return "board/boardmodify";
    }

    @PostMapping("/board/modify")
    public String boardModify(BoardDto boardDto){
        boardService.boardModifySave(boardDto);
        return "redirect:/board/list";
    }

    @ResponseBody
    @PostMapping("/uploadSummernoteImageFile")
    public JsonObject uploadSummernoteImageFile(@RequestParam("file")MultipartFile multipartFile) {
        return boardService.boardImageUpload(multipartFile);
    }


}
