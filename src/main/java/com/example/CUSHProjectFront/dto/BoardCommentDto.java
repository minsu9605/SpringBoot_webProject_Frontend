package com.example.CUSHProjectFront.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardCommentDto {

    private Long id;
    private String comment;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String writer;
    private Long boardId;
    private int cDepth;
    private Long cGroup;

    @Builder
    public BoardCommentDto(Long id, String comment, LocalDateTime createDate, LocalDateTime updateDate, String writer, Long boardId,  int cDepth, Long cGroup){
        this.id=id;
        this.comment=comment;
        this.createDate=createDate;
        this.updateDate=updateDate;
        this.writer=writer;
        this.boardId=boardId;
        this.cDepth = cDepth;
        this.cGroup = cGroup;
    }
}
