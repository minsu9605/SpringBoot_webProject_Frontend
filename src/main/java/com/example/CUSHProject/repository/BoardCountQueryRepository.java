package com.example.CUSHProject.repository;


import com.example.CUSHProject.entity.BoardCountEntity;
import com.example.CUSHProject.entity.QBoardCountEntity;
import com.example.CUSHProject.enums.Status;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardCountQueryRepository {
    private final JPAQueryFactory queryFactory;


    public List<BoardCountEntity> findByStatus(String statusName,String yearOption, String monthOption) {
        return queryFactory.selectFrom(QBoardCountEntity.boardCountEntity)
                .where(QBoardCountEntity.boardCountEntity.statusName.eq(statusName)
                    .and(QBoardCountEntity.boardCountEntity.batchDate.year().eq(Integer.parseInt(yearOption)))
                    .and(QBoardCountEntity.boardCountEntity.batchDate.month().eq(Integer.parseInt(monthOption)))
                )
                .orderBy(QBoardCountEntity.boardCountEntity.batchDate.asc())
                .fetch();

    }

}
