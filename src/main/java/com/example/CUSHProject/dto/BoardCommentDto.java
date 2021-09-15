package com.example.CUSHProject.dto;

import com.example.CUSHProject.entity.BoardCommentEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardCommentDto {

    private Long id;
    private String comment;
    private LocalDateTime createDate;
    private String writer;
    private Long boardId;

    public BoardCommentEntity toEntity(){
        return BoardCommentEntity.builder()
                .id(id)
                .comment(comment)
                .createDate(createDate)
                .build();
    }

    @Builder
    public BoardCommentDto(Long id, String comment, LocalDateTime createDate, String writer, Long boardId){
        this.id=id;
        this.comment=comment;
        this.createDate=createDate;
        this.writer=writer;
        this.boardId=boardId;
    }
}
