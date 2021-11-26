package com.example.CUSHProject.entity;

import com.example.CUSHProject.dto.BoardCountDto;
import com.example.CUSHProject.dto.BoardDto;
import com.example.CUSHProject.enums.Rating;
import com.example.CUSHProject.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@Entity
@Table(name="MS_BOARDCNT")
@SequenceGenerator(
        name = "BOARDCOUNT_SEQ_GEN",
        sequenceName = "MS_BOARDCOUNT_SEQ",
        initialValue = 1,
        allocationSize = 1
)

public class BoardCountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARDCOUNT_SEQ_GEN")
    @Column(name = "COUNT_ID")
    private Long id;

    @Column(name = "STATUS_NAME")
    private String statusName;

    @Column(name = "STATUS_COUNT")
    private Long statusCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "BATCHDATE")
    private LocalDate batchDate;


    public BoardCountDto toDto() {
        return BoardCountDto.builder()
                .id(id)
                .statusName(statusName)
                .statusCount(statusCount)
                .batchDate(batchDate)
                .build();
    }
    @Builder
    public BoardCountEntity(Long id, String statusName, Long statusCount, LocalDate batchDate ){
        this.id = id;
        this.statusName = statusName;
        this.statusCount = statusCount;
        this.batchDate = batchDate;

    }
}