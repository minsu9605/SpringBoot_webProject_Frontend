package com.example.CUSHProject.dto;

import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.enums.Rating;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private String createdDate;
    private String updatedDate;
    private int hit;
    private Rating rating;
    private String categoryName;
    private int notice;

    public BoardEntity toEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return BoardEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdDate(LocalDateTime.parse(createdDate, formatter))
                .updatedDate(LocalDateTime.parse(updatedDate, formatter))
                .hit(hit)
                .rating(rating)
                .notice(notice)
                .build();
    }

    @Builder
    public BoardDto(Long id, String writer, String title, String content, String createdDate, String updatedDate, int hit, Rating rating, String categoryName,int notice) {
        this.id=id;
        this.writer=writer;
        this.title=title;
        this.content=content;
        this.createdDate=createdDate;
        this.updatedDate=updatedDate;
        this.hit=hit;
        this.rating=rating;
        this.categoryName=categoryName;
        this.notice=notice;
    }
}
