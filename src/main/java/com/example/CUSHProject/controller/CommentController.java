package com.example.CUSHProject.controller;

import com.example.CUSHProject.dto.BoardCommentDto;
import com.example.CUSHProject.entity.BoardCommentEntity;
import com.example.CUSHProject.enums.Role;
import com.example.CUSHProject.service.BoardService;
import com.example.CUSHProject.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ResponseBody
    @PostMapping("/comment/post")
    public String commentPost(@RequestParam Long bid,
                              @RequestParam String comment,
                              Authentication authentication)throws Exception {
        int cDepth = 0;
        Long cGroup = 0L;
        commentService.commentPost(bid, comment, cDepth, cGroup,authentication.getName());
        return "success";
    }

    @ResponseBody
    @GetMapping("/comment/list")
    public HashMap<String, Object> getCommentList(@RequestParam Long bid, HttpServletRequest request){
        /*HashMap<String, Object> map = new HashMap<>();
        map.put("commentCnt",commentService.getCount(bid));
        map.put("list",commentService.getCommentList(bid));
        return map;*/
        return commentService.getCommentList(bid);
    }

    @ResponseBody
    @DeleteMapping("/comment/delete")
    public Long deleteComment(@RequestParam Long cid, HttpSession session){
        Object roleSession = session.getAttribute("memberRole");

        return commentService.deleteComment(cid,roleSession);
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
        int cDepth = 1;

        commentService.commentPost(bid, comment, cDepth, cid, authentication.getName());
        return "success";
    }
}
