package com.example.CUSHProject.controller;

import com.example.CUSHProject.Pagination.Paging;
import com.example.CUSHProject.dto.MemberDto;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.entity.MemberEntity;
import com.example.CUSHProject.service.BoardService;
import com.example.CUSHProject.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class AdminController {

    private final MemberService memberService;

    //전체회원 조회
    @GetMapping("/admin")
    public String Page(Model model,
                       @RequestParam(value ="page", defaultValue = "1") Integer curPageNum,
                       @RequestParam(required = false, defaultValue = "") String keyword) {
        Paging paging = new Paging();
        Page<MemberEntity> memberList = memberService.memberList(keyword, curPageNum);
        double listCnt = memberService.getMemberListCntByKeyword(keyword);
        paging.pageInfo(curPageNum, listCnt);
        model.addAttribute("memberList",memberList);
        model.addAttribute("paging",paging);
        return "admin/memberlist";
    }

    //회원 정보 폼
    @GetMapping("/admin/memberform")
    public String form(Model model, @RequestParam(required = false) Long id) throws Exception {
        MemberDto memberDto = memberService.findById(id);
        model.addAttribute("member", memberDto);
        return "admin/memberform";
    }

    //회원 정보 수정
    @PostMapping("/admin/memberform")
    public String updateform(MemberDto memberDto){
        memberService.memberUpdate(memberDto);
        return "redirect:/";
    }

    //회원탈퇴
    @ResponseBody
    @DeleteMapping("/admin/withdrawal")
    public Long withdrawalMember(@RequestParam(required = false) Long id) {
        memberService.deleteUser(id);
        return id;
    }



}
