package com.example.CUSHProject.controller;

import com.example.CUSHProject.dto.BoardCommentDto;
import com.example.CUSHProject.entity.BoardCommentEntity;
import com.example.CUSHProject.service.BoardService;
import com.example.CUSHProject.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ResponseBody
    @PostMapping("/comment/post")
    public String commentPost(@RequestParam Long bid, @RequestParam String comment, Authentication authentication)throws Exception {

        BoardCommentDto boardCommentDto = new BoardCommentDto();

        boardCommentDto.setComment(comment);
        boardCommentDto.setBoardId(bid);
        boardCommentDto.setCDepth(0);

        commentService.commentPost(boardCommentDto, authentication.getName());
        return "success";
    }

    @ResponseBody
    @GetMapping("/comment/list")
    public HashMap<String, Object> getCommentList(@RequestParam Long bid)throws Exception{
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",commentService.getCommentList(bid));
        return map;
    }

    @ResponseBody
    @DeleteMapping("/comment/delete")
    public Long deleteComment(@RequestParam Long cid){
        return commentService.deleteComment(cid);
    }

    @ResponseBody
    @PutMapping("/comment/modify")
    public String modifyComment(@RequestParam Long cid, @RequestParam String comment){
        commentService.modifyComment(cid, comment);
        return "success";
    }

    @ResponseBody
    @GetMapping("/comment/reComment/list")
    public HashMap<String, Object> getReCommentList(@RequestParam Long cid)throws Exception{
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",commentService.getReCommentList(cid));
        return map;
    }

    @ResponseBody
    @PostMapping("/comment/reComment/post")
    public String reCommentPost(@RequestParam Long bid,
                                @RequestParam String comment,
                                @RequestParam Long cid, Authentication authentication)throws Exception {

        BoardCommentDto boardCommentDto = new BoardCommentDto();

        boardCommentDto.setComment(comment);
        boardCommentDto.setBoardId(bid);
        boardCommentDto.setCDepth(1);

        boardCommentDto.setCGroup(cid);

        commentService.commentPost(boardCommentDto, authentication.getName());
        return "success";
    }
}
