package com.example.CUSHProject.dto;

import com.example.CUSHProject.entity.BoardCountEntity;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.enums.Rating;
import com.example.CUSHProject.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class BoardCountDto {
    private Long id;
    private String statusName;
    private Long statusCount;
    private LocalDateTime batchDate;

    public BoardCountEntity toEntity() {
        return BoardCountEntity.builder()
                .id(id)
                .statusName(statusName)
                .statusCount(statusCount)
                .batchDate(batchDate)
                .build();
    }

    @Builder
    public BoardCountDto(Long id, String statusName, Long statusCount, LocalDateTime batchDate) {
        this.id=id;
        this.statusName=statusName;
        this.statusCount=statusCount;
        this.batchDate=batchDate;

    }
}
