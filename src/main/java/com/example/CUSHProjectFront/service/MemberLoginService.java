package com.example.CUSHProjectFront.service;

import com.example.CUSHProjectFront.dto.MemberDto;
import com.example.CUSHProjectFront.handler.MemberHandler;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberLoginService implements UserDetailsService {

    private final MemberHandler memberHandler;

    //로그인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberDto memberDto = memberHandler.getMemberByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(memberDto.getRole().getValue()));
        return new User(memberDto.getUsername(), memberDto.getPassword(), authorities);
    }
}
