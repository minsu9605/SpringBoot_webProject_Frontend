package com.example.CUSHProjectFront.dto;

import com.example.CUSHProjectFront.enums.Rating;
import com.example.CUSHProjectFront.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestDto {
    private int id;
    private String writer;


    @Builder
    public TestDto(int id, String writer, String title, String content, String createdDate, String updatedDate, int hit, int price, Rating rating, Status status, String categoryName, Long categoryId, String writeIp, double myLat, double myLng, int alertRead) {
        this.id=id;
        this.writer=writer;

    }
}
