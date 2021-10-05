package com.example.CUSHProject.entity;

import com.example.CUSHProject.dto.BoardDto;
import com.example.CUSHProject.enums.Rating;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@Entity
@Table(name="BOARD")
@SequenceGenerator(
        name = "BOARD_SEQ_GEN",
        sequenceName = "BOARD_SEQ",
        initialValue = 1,
        allocationSize = 1
)

public class BoardEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GEN")
    @Column(name = "BOARD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Board_WRITER")
    private MemberEntity writer;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "CREATEDDATE")
    private LocalDateTime createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "UPDATEDDATE")
    private LocalDateTime updatedDate;

    @Column(name = "HIT")
    private int hit;

    @Column(name = "NOTICE")
    private int notice;

    @Enumerated(EnumType.STRING)
    @Column(name = "RATING")
    private Rating rating;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private BoardCategoryEntity category;

    public void setCategory(BoardCategoryEntity category){
        this.category=category;
    }
    public void setWriter(MemberEntity memberEntity){
        this.writer=memberEntity;
    }

    public BoardDto toDto() {
        return BoardDto.builder()
                .id(id)
                .title(title)
                .writer(writer.getNickname())
                .content(content)
                .createdDate(createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updatedDate(updatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .hit(hit)
                .rating(rating)
                .categoryName(category.getName())
                .notice(notice)
                .build();
    }
    @Builder
    public BoardEntity(Long id, String title, String content, LocalDateTime createdDate, LocalDateTime updatedDate,int hit, Rating rating, int notice) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.hit = hit;
        this.rating = rating;
        this.notice = notice;
    }
}
