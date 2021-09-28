package com.example.CUSHProject.repository;


import com.example.CUSHProject.entity.QBoardCommentEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Repository
public class BoardCommentQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void updateComment(Long cid, String comment){
        queryFactory.update(QBoardCommentEntity.boardCommentEntity)
                .set(QBoardCommentEntity.boardCommentEntity.comment, comment)
                .set(QBoardCommentEntity.boardCommentEntity.updateDate, LocalDateTime.now())
                .where(QBoardCommentEntity.boardCommentEntity.id.eq(cid))
                .execute();
    }
}
