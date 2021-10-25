package com.example.CUSHProject.repository;

import com.example.CUSHProject.entity.*;
import com.example.CUSHProject.enums.Status;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository{
    private final JPAQueryFactory queryFactory;

    /*조회수 증가 쿼리*/
    @Transactional
    public void boardHitUpdate(Long id){
        queryFactory.update(QBoardEntity.boardEntity)
                .set(QBoardEntity.boardEntity.hit, QBoardEntity.boardEntity.hit.add(1))
                .where(QBoardEntity.boardEntity.id.eq(id))
                .execute();
    }

    /*판매완료 버튼*/
    @Transactional
    public void boardStatusUpdate(Long id){
        queryFactory.update(QBoardEntity.boardEntity)
                .set(QBoardEntity.boardEntity.status, Status.soldOut)
                .where(QBoardEntity.boardEntity.id.eq(id))
                .execute();
    }

    /*조회된 리스트 전체 크기*/
    public Long getTotalCount(BoardCategoryEntity boardCategoryEntity,String searchType, String keyword){
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.category.eq(boardCategoryEntity)
                        .and(eqSearchType(searchType,keyword))
                )
                .fetchCount();
    }

    /*내가 쓴글 조회된 리스트 전체 크기*/
    public Long getMyBoardTotalCount(MemberEntity memberEntity,String searchType, String keyword){
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.writer.eq(memberEntity)
                        .and(eqSearchType(searchType,keyword)))
                .fetchCount();
    }

    /*내가 쓴글 한페이지 출력 리스트*/
    public List<BoardEntity> getMyBoardList(MemberEntity memberEntity, int page, int perPage, String searchType, String keyword){
        int start = (page * perPage) - perPage;
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.writer.eq(memberEntity)
                        .and(eqSearchType(searchType,keyword))
                )
                .orderBy(QBoardEntity.boardEntity.id.desc())
                .offset(start)
                .limit(perPage)
                .fetch();
    }

    /*한페이지 출력 리스트*/
    public List<BoardEntity> getBoardList(BoardCategoryEntity boardCategoryEntity, int page, int perPage, String searchType, String keyword){
        int start = (page * perPage) - perPage;
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.category.eq(boardCategoryEntity)
                    .and(eqSearchType(searchType,keyword))
                )
                .orderBy(QBoardEntity.boardEntity.id.desc())
                .offset(start)
                .limit(perPage)
                .fetch();
    }

    /*검색조건 분기 함수*/
    private BooleanExpression eqSearchType(String searchType, String keyword){
        if(!keyword.equals("")){
            switch (searchType) {
                case "title":
                    return QBoardEntity.boardEntity.title.contains(keyword);
                case "content":
                    return QBoardEntity.boardEntity.content.contains(keyword);
                case "writer":
                    return QBoardEntity.boardEntity.writer.nickname.eq(keyword);
            }
        }
        return null;
    }

    /*한페이지 출력 리스트*/
    public List<BoardEntity> getMapList(double startLat, double startLng, double endLat, double endLng){
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.status.eq(Status.sell)
                        .and(QBoardEntity.boardEntity.myLat.between(startLat,endLat))
                        .and(QBoardEntity.boardEntity.myLng.between(startLng,endLng))
                )
                .orderBy(QBoardEntity.boardEntity.id.desc())
                .fetch();
    }

}
