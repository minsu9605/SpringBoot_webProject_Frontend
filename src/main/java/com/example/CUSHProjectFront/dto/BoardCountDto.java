package com.example.CUSHProjectFront.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BoardCountDto {
    private Long id;
    private String statusName;
    private Long statusCount;
    private LocalDate batchDate;

    @Builder
    public BoardCountDto(Long id, String statusName, Long statusCount, LocalDate batchDate) {
        this.id=id;
        this.statusName=statusName;
        this.statusCount=statusCount;
        this.batchDate=batchDate;

    }
}
