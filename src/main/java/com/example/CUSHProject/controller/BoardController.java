package com.example.CUSHProject.controller;

import com.example.CUSHProject.Pagination.Paging;
import com.example.CUSHProject.dto.BoardCategoryDto;
import com.example.CUSHProject.dto.BoardDto;
import com.example.CUSHProject.entity.BoardCategoryEntity;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.repository.BoardCategoryRepository;
import com.example.CUSHProject.service.BoardService;
import com.example.CUSHProject.service.CategoryService;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;


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
        BoardDto boardForm = new BoardDto();
        List<BoardCategoryDto> categoryList = categoryService.getCategory();
        model.addAttribute("boardForm",boardForm);
        model.addAttribute("categoryList",categoryList);
        return "board/boardform";
    }

    @PostMapping("/board/write")
    public String boardWrite(BoardDto boardDto){
        boardService.boardWrite(boardDto);
        return "redirect:/board/list";
    }

    @GetMapping("/board/content")
    public String boardContent(Model model, @RequestParam(required = false) Long id){
        boardService.boardHitUpdate(id);
        BoardDto boardForm = boardService.boardContent(id);
        model.addAttribute("boardForm",boardForm);
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
