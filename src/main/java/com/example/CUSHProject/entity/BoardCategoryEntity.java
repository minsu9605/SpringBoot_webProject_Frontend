package com.example.CUSHProject.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name="BCATEGORY")
@SequenceGenerator(
        name = "BCATEGORY_SEQ_GEN",
        sequenceName = "BCATEGORY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class BoardCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BCATEGORY_SEQ_GEN")
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(name = "CATEGORY_NAME")
    private String name;


    @Builder
    public BoardCategoryEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
