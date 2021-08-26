package com.example.CUSHProject.repository;

import com.example.CUSHProject.dto.MemberDto;
import com.example.CUSHProject.entity.MemberEntity;
import com.example.CUSHProject.entity.QMemberEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {
    private final JPAQueryFactory queryFactory;

    public MemberEntity findByUsername(String username) {
        return queryFactory.selectFrom(QMemberEntity.memberEntity)
                .where(QMemberEntity.memberEntity.username.eq(username))
                .fetchOne();
    }

    //본인것 제외한 아이디 찾기
    public QueryResults<String> findExistUsername(Long id) {
        return queryFactory.select(QMemberEntity.memberEntity.username)
                .from(QMemberEntity.memberEntity)
                .where(QMemberEntity.memberEntity.id.ne(id))
                .fetchResults();
    }

    //본인것 제외한 닉네임 찾기
    public QueryResults<String> findExistNickname(Long id) {
        return queryFactory.select(QMemberEntity.memberEntity.nickname)
                .from(QMemberEntity.memberEntity)
                .where(QMemberEntity.memberEntity.id.ne(id))
                .fetchResults();
    }

    //검색어를 통해 아이디찾기
    public double findByKeyword(String keyword) {
        return queryFactory.selectFrom(QMemberEntity.memberEntity)
                .where(QMemberEntity.memberEntity.username.contains(keyword))
                .fetchCount();
    }

    @Transactional
    public void memberDelete(Long id) {
        queryFactory.delete(QMemberEntity.memberEntity)
                .where(QMemberEntity.memberEntity.id.eq(id))
                .execute();
    }

    @Transactional
    public void updateMemberInfo(MemberDto memberDto) {
        queryFactory.update(QMemberEntity.memberEntity)
                .set(QMemberEntity.memberEntity.id, memberDto.getId())
                .set(QMemberEntity.memberEntity.username, memberDto.getUsername())
                .set(QMemberEntity.memberEntity.nickname, memberDto.getNickname())
                .set(QMemberEntity.memberEntity.birth, memberDto.getYear() + "-" + memberDto.getMonth()+ "-" + memberDto.getDay())
                .set(QMemberEntity.memberEntity.age, memberDto.calAge(memberDto.getYear() + memberDto.getMonth() + memberDto.getDay()))
                .set(QMemberEntity.memberEntity.gender, memberDto.getGender())
                .set(QMemberEntity.memberEntity.role, memberDto.getRole())
                .where(QMemberEntity.memberEntity.id.eq(memberDto.getId()))
                .execute();
    }

    @Transactional
    public void updateMemberPassword(MemberDto memberDto) {
        queryFactory.update(QMemberEntity.memberEntity)
                .set(QMemberEntity.memberEntity.password, memberDto.getPassword())
                .where(QMemberEntity.memberEntity.username.eq(memberDto.getUsername()))
                .execute();
    }


}
