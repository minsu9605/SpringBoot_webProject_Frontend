package com.example.CUSHProject.repository;

import com.example.CUSHProject.entity.BoardCategoryEntity;
import com.example.CUSHProject.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardCategoryRepository extends JpaRepository<BoardCategoryEntity, Long> {
    Optional<BoardCategoryEntity> findById(Long id);
    List<BoardCategoryEntity> findAll();
}
