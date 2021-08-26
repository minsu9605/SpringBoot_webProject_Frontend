package com.example.CUSHProject.repository;

import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.entity.QBoardEntity;
import com.example.CUSHProject.entity.QMemberEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository{
    private final JPAQueryFactory queryFactory;

    //검색어를 통해 아이디찾기
    public double findByKeyword(String keyword) {
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.title.contains(keyword).or(QBoardEntity.boardEntity.content.contains(keyword)))
                .fetchCount();
    }


}
