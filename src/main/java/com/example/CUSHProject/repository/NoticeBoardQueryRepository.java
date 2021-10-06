package com.example.CUSHProject.repository;

import com.example.CUSHProject.entity.QBoardEntity;
import com.example.CUSHProject.entity.QNoticeBoardEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class NoticeBoardQueryRepository {
    private final JPAQueryFactory queryFactory;


    /*조회수 증가 쿼리*/
    @Transactional
    public void noticeHitUpdate(Long id){
        queryFactory.update(QNoticeBoardEntity.noticeBoardEntity)
                .set(QNoticeBoardEntity.noticeBoardEntity.hit, QNoticeBoardEntity.noticeBoardEntity.hit.add(1))
                .where(QNoticeBoardEntity.noticeBoardEntity.id.eq(id))
                .execute();
    }
}
