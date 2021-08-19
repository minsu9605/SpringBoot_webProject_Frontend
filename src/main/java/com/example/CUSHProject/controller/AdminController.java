package com.example.CUSHProject.controller;

import com.example.CUSHProject.dto.MemberDto;
import com.example.CUSHProject.entity.MemberEntity;
import com.example.CUSHProject.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class AdminController {

    private final MemberService memberService;

    //전체회원 조회
    @GetMapping("/admin")
    public String list(@PageableDefault Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String keyword,
                       Model model) {
        Page<MemberEntity> memberList = memberService.memberList(keyword, pageable);
        int startPage = Math.max(1, memberList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(memberList.getTotalPages(), memberList.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("memberList", memberList);

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

    /*//회원탈퇴
    @PostMapping("/admin/withdrawal")
    public String withdrawalMember(@RequestParam(required = false) Long id) {
        memberService.deleteUser(id);
        return "admin/memberlist";
    }*/
}
