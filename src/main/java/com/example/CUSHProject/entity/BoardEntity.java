package com.example.CUSHProject.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GEN")
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "BOARD_DATE")
    private Timestamp date;

    @Column(name = "HIT")
    private int hit;

    @Column(name = "RATING")
    private int rating;

    @Builder
    public BoardEntity(Long id, String username, String title, String content, Timestamp date, int hit, int rating) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.content = content;
        this.date = date;
        this.hit = hit;
        this.rating = rating;
    }
}
