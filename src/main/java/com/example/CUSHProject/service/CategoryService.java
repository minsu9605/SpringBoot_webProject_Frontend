package com.example.CUSHProject.service;

import com.example.CUSHProject.dto.BoardCategoryDto;
import com.example.CUSHProject.entity.BoardCategoryEntity;
import com.example.CUSHProject.entity.MemberEntity;
import com.example.CUSHProject.repository.BoardCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final BoardCategoryRepository boardCategoryRepository;

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
}
