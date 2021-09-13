package com.example.CUSHProject.entity;

import com.example.CUSHProject.enums.Rating;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "WRITER")
    private String writer;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "CREATEDDATE")
    private LocalDateTime createdDate;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "UPDATEDDATE")
    private LocalDateTime updatedDate;

    @Column(name = "HIT")
    private int hit;

    @Enumerated(EnumType.STRING)
    @Column(name = "RATING")
    private Rating rating;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private BoardCategoryEntity category;

    public void setCategory(BoardCategoryEntity category){
        this.category=category;
    }

    @Builder
    public BoardEntity(Long id, String writer, String title, String content, LocalDateTime createdDate, LocalDateTime updatedDate,int hit, Rating rating) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.hit = hit;
        this.rating = rating;
    }
}
