package com.example.CUSHProject.repository;

import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.entity.QBoardEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository{
    private final JPAQueryFactory queryFactory;

    //키워드 통해 갯수 찾기
    public double findByKeyword(String keyword) {
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.title.contains(keyword)
                        .or(QBoardEntity.boardEntity.content.contains(keyword))
                        .or(QBoardEntity.boardEntity.writer.contains(keyword))
                )
                .fetchCount();
    }


    /*조회수 증가 쿼리*/
    @Transactional
    public void boardHitUpdate(Long id){
        queryFactory.update(QBoardEntity.boardEntity)
                .set(QBoardEntity.boardEntity.hit, QBoardEntity.boardEntity.hit.add(1))
                .where(QBoardEntity.boardEntity.id.eq(id))
                .execute();
    }
}
