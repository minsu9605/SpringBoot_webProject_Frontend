package com.example.CUSHProject.validator;

import com.example.CUSHProject.dto.MemberDto;
import com.example.CUSHProject.repository.MemberQueryRepository;
import com.example.CUSHProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component //DI사용
@RequiredArgsConstructor
public class ModifyValidator implements Validator {

    public final MemberRepository memberRepository;

    public final MemberQueryRepository memberQueryRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(MemberDto.class);
    }

    //검사내용
    @Override
    public void validate(Object object, Errors errors) {
        MemberDto memberDto = (MemberDto) object;


        //이름 관련
        if(memberDto.getNickname().equals("admin")) {
            errors.rejectValue("nickname","unavailable","사용할 수 없는 이름입니다.");
        }

        /*if(memberQueryRepository.findExistNickname(memberDto.getId()).getResults(){
            errors.rejectValue("nickname", "invalid username", "이미 사용중인 이름입니다.");

        }*/

        /*if(memberQueryRepository.findExistNickname(memberDto.getId()).getResults().contains(memberDto.getNickname())){
            errors.rejectValue("nickname", "invalid username", "이미 사용중인 이름입니다.");

        }*/

    }
}

