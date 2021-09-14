package com.example.CUSHProject.service;

import com.example.CUSHProject.dto.BoardCategoryDto;
import com.example.CUSHProject.dto.BoardDto;
import com.example.CUSHProject.entity.BoardCategoryEntity;
import com.example.CUSHProject.entity.MemberEntity;
import com.example.CUSHProject.repository.BoardCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final BoardCategoryRepository boardCategoryRepository;
    private final BoardService boardService;

    public List<BoardCategoryDto> getCategory() {
        List<BoardCategoryEntity> boardCategoryRepositoryAll = boardCategoryRepository.findAll();
        List<BoardCategoryDto> boardCategoryDtoList = new ArrayList<>();

        for(BoardCategoryEntity category: boardCategoryRepositoryAll){
            BoardCategoryDto boardCategoryDto = BoardCategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();
            boardCategoryDtoList.add(boardCategoryDto);
        }
        return boardCategoryDtoList;

    }

    public String findCategoryById(Long id){
        Optional<BoardCategoryEntity> boardCategoryEntity = boardCategoryRepository.findById(id);
        return boardCategoryEntity.get().getName();
    }
}
