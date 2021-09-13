package com.example.CUSHProject.dto;

import com.example.CUSHProject.entity.BoardCategoryEntity;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.enums.Rating;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardCategoryDto {
    private Long id;
    private String name;

    public BoardCategoryEntity toEntity() {
        return BoardCategoryEntity.builder()
                .id(id)
                .name(name)
                .build();
    }

    @Builder
    public BoardCategoryDto(Long id, String name) {
        this.id=id;
        this.name=name;

    }
}
