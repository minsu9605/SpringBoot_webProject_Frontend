package com.example.CUSHProject.repository;

import com.example.CUSHProject.entity.BoardCommentEntity;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity, Long> {
    List<BoardCommentEntity> findByBoardId(BoardEntity boardEntity);

    boolean existsByBoardId(BoardEntity boardEntity);
    boolean existsByWriter(MemberEntity memberEntity);

}
