package com.example.CUSHProject.entity;

import com.example.CUSHProject.dto.BoardCommentDto;
import com.example.CUSHProject.enums.Rating;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name="BCOMMENT")
@SequenceGenerator(
        name = "BCOMMENT_SEQ_GEN",
        sequenceName = "BCOMMENT_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class BoardCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BCOMMENT_SEQ_GEN")
    @Column(name = "CID")
    private Long id;

    @Column(name = "CONTENT")
    private String comment;

    @Column(name = "CREATEDATE")
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "COMMENT_WRITER")
    private MemberEntity writer;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private BoardEntity boardId;

    public void setCommentWriter(MemberEntity memberEntity){
        this.writer=memberEntity;
    }

    public void setCommentBoardId(BoardEntity boardEntity){
        this.boardId=boardEntity;
    }

    public BoardCommentDto toDto() {
        return BoardCommentDto.builder()
                .id(id)
                .comment(comment)
                .createDate(createDate)
                .writer(writer.getNickname())
                .boardId(boardId.getId())
                .build();
    }


    @Builder
    public BoardCommentEntity(Long id, String comment, LocalDateTime createDate) {
        this.id = id;
        this.comment = comment;
        this.createDate = createDate;
    }
}
