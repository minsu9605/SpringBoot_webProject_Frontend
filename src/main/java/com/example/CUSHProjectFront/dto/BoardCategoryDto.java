package com.example.CUSHProjectFront.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BoardCategoryDto {
    private Long id;
    private String name;

    @Builder
    public BoardCategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;

    }
}
