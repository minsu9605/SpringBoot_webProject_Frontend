package com.example.CUSHProject.controller;

import com.example.CUSHProject.dto.BoardCategoryDto;
import com.example.CUSHProject.dto.BoardDto;
import com.example.CUSHProject.service.BoardService;
import com.example.CUSHProject.service.CategoryService;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    /*일반 게시판*/
    @GetMapping("/board/list")
    public String boardList(Model model) {
        model.addAttribute("categoryList",categoryService.getCategory());
        return "board/boardlist";
    }

    /*일반 게시판 api*/
    @GetMapping("/board/list/table")
    @ResponseBody
    public HashMap<String, Object> getBoardList(@RequestParam(required = false) Long categoryId){
        HashMap<String, Object> map = new HashMap<>();

        List<BoardDto> boardDtoList = boardService.getBoardList(categoryId);
        map.put("list", boardDtoList);
        return map;
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
    public String boardWrite(BoardDto boardDto, Authentication authentication){
        boardService.boardWrite(boardDto, authentication.getName());
        return "redirect:/board/list?category=1";
    }

    @GetMapping("/board/content")
    public String boardContent(Model model, @RequestParam(required = false) Long id){
        boardService.boardHitUpdate(id);
        BoardDto boardForm = boardService.boardContent(id);

        model.addAttribute("categoryList",categoryService.getCategory());
        model.addAttribute("boardForm",boardForm);
        return "board/boardcontent";
    }

    @GetMapping("/board/modify")
    public String boardModify(Model model, @RequestParam(required = false) Long id){
        BoardDto boardForm = boardService.boardContent(id);

        List<BoardCategoryDto> categoryList = categoryService.getCategory();
        model.addAttribute("boardForm",boardForm);
        model.addAttribute("categoryList",categoryList);
        return "board/boardmodify";
    }

    @PostMapping("/board/modify")
    public String boardModify(BoardDto boardDto, Authentication authentication){
        boardService.boardModifySave(boardDto, authentication.getName());
        return "redirect:/board/list?category=1";
    }

    @ResponseBody
    @PostMapping("/uploadSummernoteImageFile")
    public JsonObject uploadSummernoteImageFile(@RequestParam("file")MultipartFile multipartFile) {
        return boardService.boardImageUpload(multipartFile);
    }

    @ResponseBody
    @DeleteMapping("/board/delete")
    public void boardDelete(@RequestParam(required = false)Long id) {
        boardService.boardDelete(id);
    }

}
