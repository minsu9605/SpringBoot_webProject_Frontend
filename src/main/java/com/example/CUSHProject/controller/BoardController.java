package com.example.CUSHProject.controller;

import com.example.CUSHProject.Pagination.Paging;
import com.example.CUSHProject.dto.BoardDto;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @RequestParam(defaultValue = "1") Integer curPageNum,
                            @RequestParam(defaultValue = "title") String searchType,
                            @RequestParam(required = false, defaultValue = "")String keyword) {
        Paging paging = new Paging();
        Page<BoardEntity> boardList = boardService.boardList(searchType, keyword, curPageNum);

        double listcnt = boardService.getBoardListCnt(keyword);
        paging.pageInfo(curPageNum, listcnt);
        model.addAttribute("boardList",boardList);
        model.addAttribute("paging",paging);
        return "board/boardlist";
    }

    @GetMapping("/board/form")
    public String boardForm(Model model, @RequestParam(required = false) Long id){
        BoardDto boardForm = boardService.boardForm(id);
        model.addAttribute("boardForm",boardForm);
        return "board/boardform";
    }

}
