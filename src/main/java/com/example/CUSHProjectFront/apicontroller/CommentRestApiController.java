package com.example.CUSHProjectFront.apicontroller;

import com.example.CUSHProjectFront.handler.CommentRestApiHandler;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@AllArgsConstructor
public class CommentRestApiController {

    private final CommentRestApiHandler commentRestApiHandler;

    @GetMapping("/api/comment/list")
    public HashMap<String, Object> getCommentList(@RequestParam Long bid) throws Exception {
        return commentRestApiHandler.getCommentList(bid);
    }

    @PostMapping("/api/comment/post")
    public HashMap<String, Object> commentPost(@RequestParam Long bid,
                                               @RequestParam String comment,
                                               Authentication authentication) throws Exception {

        return commentRestApiHandler.commentPost(bid, comment, authentication.getName());
    }

    @GetMapping("/api/comment/reComment/list")
    public HashMap<String, Object> getReCommentList(@RequestParam Long cid) throws Exception {
        return commentRestApiHandler.getReCommentList(cid);
    }

    @PostMapping("/api/comment/reComment/post")
    public HashMap<String, Object> reCommentPost(@RequestParam Long bid,
                                                 @RequestParam Long cid,
                                                 @RequestParam String comment,
                                                 Authentication authentication) throws Exception {

        return commentRestApiHandler.reCommentPost(bid, cid, comment, authentication.getName());
    }

    @DeleteMapping("/api/comment/delete")
    public HashMap<String, Object> deleteComment(@RequestParam Long cid, HttpSession session) throws Exception {
        return commentRestApiHandler.deleteComment(cid, session.getAttribute("memberRole"));
    }

    @GetMapping("/api/comment/modify")
    public HashMap<String, Object> modifyComment(@RequestParam Long cid, String comment) throws Exception {
        return commentRestApiHandler.modifyComment(cid, comment);
    }
}
