package com.example.CUSHProject.dto;

import com.example.CUSHProject.entity.BoardEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private Timestamp date;
    private int hit;
    private int rating;

    public BoardEntity toEntity() {
        return BoardEntity.builder()
                .id(id)
                .username(username)
                .title(title)
                .content(content)
                .date(date)
                .hit(hit)
                .rating(rating)
                .build();
    }

    @Builder
    public BoardDto(Long id, String username, String title, String content, Timestamp date, int hit, int rating) {
        this.id=id;
        this.username=username;
        this.title=title;
        this.content=content;
        this.date=date;
        this.hit=hit;
        this.rating=rating;
    }
}
