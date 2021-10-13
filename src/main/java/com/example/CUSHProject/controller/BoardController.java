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

import javax.servlet.http.HttpServletRequest;
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
        model.addAttribute("categoryList",categoryService.getCategoryList());
        return "board/boardlist";
    }

    /*일반 게시판 api*/
    @GetMapping("/board/list/table")
    @ResponseBody
    public HashMap<String, Object> getBoardList(@RequestParam(required = false) Long categoryId,
                                                @RequestParam(required = false) int page,
                                                @RequestParam(required = false) int perPage,
                                                @RequestParam(required = false) String searchType,
                                                @RequestParam(required = false, defaultValue = "") String keyword
                                                ){
        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();


        int total = boardService.getTotalSize(categoryId,searchType,keyword);
        List<BoardDto> boardDtoList = boardService.getBoardList(categoryId, page, perPage, searchType, keyword);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", boardDtoList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", page);
        paginationMap.put("totalCount", total);
        return objectMap;
    }

    //글쓰기
    @GetMapping("/board/write")
    public String boardWrite(Model model) {
        BoardDto boardForm = new BoardDto();
        List<BoardCategoryDto> categoryList = categoryService.getCategoryList();
        model.addAttribute("boardForm",boardForm);
        model.addAttribute("categoryList",categoryList);
        return "board/boardform";
    }

    @PostMapping("/board/write")
    public String boardWrite(BoardDto boardDto, Authentication authentication, HttpServletRequest request){
        boardService.boardWrite(boardDto, authentication.getName(), request);
        return "redirect:/board/list?category=1";
    }

   /* @ResponseBody
    @GetMapping("/board/write/map")
    public String boardMap(@RequestParam(required = false) double myLat,
                           @RequestParam(required = false) double myLng){
        boardService.saveMap(myLat, myLng);

        System.out.println("확인"+myLat);
        System.out.println("확인"+myLng);
        return "a";
    }*/

    @GetMapping("/board/content")
    public String boardContent(Model model, @RequestParam(required = false) Long id){
        boardService.boardHitUpdate(id);
        BoardDto boardForm = boardService.boardContent(id);

        model.addAttribute("categoryList",categoryService.getCategoryList());
        model.addAttribute("boardForm",boardForm);
        return "board/boardcontent";
    }

    @GetMapping("/board/modify")
    public String boardModify(Model model, @RequestParam(required = false) Long id){
        BoardDto boardForm = boardService.boardContent(id);

        List<BoardCategoryDto> categoryList = categoryService.getCategoryList();
        model.addAttribute("boardForm",boardForm);
        model.addAttribute("categoryList",categoryList);
        return "board/boardmodify";
    }

    @PostMapping("/board/modify")
    public String boardModify(BoardDto boardDto, Authentication authentication){
        boardService.boardModifySave(boardDto, authentication.getName());
        return "redirect:/board/list?category=1";
    }

    @GetMapping("/board/map")
    public String showMap(){
        return "board/map";
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
