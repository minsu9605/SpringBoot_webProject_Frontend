package com.example.CUSHProject.repository;

import com.example.CUSHProject.entity.BoardCategoryEntity;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.entity.MemberEntity;
import com.example.CUSHProject.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByCategoryOrderByIdDesc(BoardCategoryEntity categoryId);
    Page<BoardEntity> findByUpdatedDateBeforeAndStatusEquals(LocalDateTime localDateTime, Status status, Pageable pageable);
    Page<Long> countByStatus(LocalDateTime localDateTime, Status status, Pageable pageable);
    List<BoardEntity> findByWriter(MemberEntity memberEntity);

    boolean existsByWriter(MemberEntity memberEntity);

}
