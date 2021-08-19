package com.example.CUSHProject.service;

import com.example.CUSHProject.dto.MemberDto;
import com.example.CUSHProject.entity.MemberEntity;
import com.example.CUSHProject.enums.Role;
import com.example.CUSHProject.repository.MemberQueryRepository;
import com.example.CUSHProject.repository.MemberRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final MemberQueryRepository memberQueryRepository;



    @Transactional
    public Long singUp(MemberDto memberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        //memberDto.setRole(Role.ROLE_MEMBER.getValue());
        memberDto.setRole(Role.ROLE_MEMBER);

        /*if(memberDto.getNickname().equals("admin")) {
            memberDto.setRole(Role.ADMIN.getValue());
        } else {
            memberDto.setRole(Role.MEMBER.getValue());
        }*/
        return memberRepository.save(memberDto.toEntity()).getId();
    }


    //로그인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> memberEntityWrapper = memberRepository.findByUsername(username);
        MemberEntity memberEntity = memberEntityWrapper.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(memberEntity.getRole().getValue()));
        return new User(memberEntity.getUsername(), memberEntity.getPassword(), authorities);
    }


    public MemberDto memberInfo(String email){
       MemberEntity memberEntity = memberQueryRepository.findByUsername(email);

       String[] str = memberEntity.getBirth().split("-");

       MemberDto memberDto = MemberDto.builder()
               .id(memberEntity.getId())
               .username(memberEntity.getUsername())
               .password(memberEntity.getPassword())
               .nickname(memberEntity.getNickname())
               .year(str[0])
               .month(str[1])
               .day(str[2])
               .gender(memberEntity.getGender())
               .role(memberEntity.getRole())
               //.role(memberEntity.getRole().getValue())
               .build();
        return memberDto;
    }

    public MemberDto findById(Long id){
        Optional<MemberEntity> memberEntity = memberRepository.findById(id);

        String[] str = memberEntity.get().getBirth().split("-");

        MemberDto memberDto = MemberDto.builder()
                .id(memberEntity.get().getId())
                .username(memberEntity.get().getUsername())
//              .password(memberEntity.getPassword())
                .nickname(memberEntity.get().getNickname())
                .year(str[0])
                .month(str[1])
                .day(str[2])
                .gender(memberEntity.get().getGender())
                .role(memberEntity.get().getRole())
            //    .role(memberEntity.get().getRole().getValue())
                .build();
        return memberDto;
    }

    public void memberUpdate(MemberDto memberDto) {
        memberQueryRepository.updateMemberInfo(memberDto);
    }

    //패스워드 변경 전 기존 패스워드 검사
    public HashMap<String, Object> pwCheck(Authentication authentication, String original_Pw) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String db_Pw = memberInfo(authentication.getName()).getPassword();
        HashMap<String, Object> map = new HashMap<>();
        map.put("result",passwordEncoder.matches(original_Pw, db_Pw));
        return map;
    }

    //패스워드 변경
    public void passwordUpdate(MemberDto memberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberQueryRepository.updateMemberPassword(memberDto);
    }

    //멤버 리스트 페이징 & 검색
    public Page<MemberEntity> memberList(String keyword, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()) - 1;
        pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        return memberRepository.findByUsernameContaining(keyword, pageable);
    }

    //멤버 탈퇴
    public void deleteUser(Long id){
        memberQueryRepository.memberDelete(id);
    }

    //email 중복 검사
    public HashMap<String, Object> usernameOverlap(String username) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("result",memberRepository.existsByUsername(username));
        System.out.println("불린 : " +map);
        return map;
    }

    //닉네임 중복 검사
    public HashMap<String, Object> nicknameOverlap(String nickname) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("result",memberRepository.existsByNickname(nickname));
        return map;
    }

    //아이디 수정 중복 검사
    public HashMap<String, Object> usernameModify(String username, Long id) {
        List<String> findUsername = memberQueryRepository.findExistUsername(id).getResults();

        HashMap<String, Object> map = new HashMap<>();
        map.put("result",findUsername.contains(username));
        return map;
    }

    //닉네임 수정 중복 검사
    public HashMap<String, Object> nicknameModify(String nickname, Long id) {
        List<String> findNickname = memberQueryRepository.findExistNickname(id).getResults();

        HashMap<String, Object> map = new HashMap<>();
        map.put("result",findNickname.contains(nickname));
        return map;
    }

}
