package com.example.CUSHProjectFront.dto;

import com.example.CUSHProjectFront.enums.Rating;
import com.example.CUSHProjectFront.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private String createdDate;
    private String updatedDate;
    private int hit;
    private int price;
    private Rating rating;
    private Status status;
    private String categoryName;
    private Long categoryId;
    private String writeIp;
    private double myLat;
    private double myLng;
    private int alertRead;

    @Builder
    public BoardDto(Long id, String writer, String title, String content, String createdDate, String updatedDate, int hit, int price, Rating rating, Status status, String categoryName, Long categoryId, String writeIp, double myLat, double myLng,int alertRead) {
        this.id=id;
        this.writer=writer;
        this.title=title;
        this.content=content;
        this.createdDate=createdDate;
        this.updatedDate=updatedDate;
        this.hit=hit;
        this.price=price;
        this.rating=rating;
        this.status=status;
        this.categoryName=categoryName;
        this.categoryId=categoryId;
        this.writeIp=writeIp;
        this.myLat = myLat;
        this.myLng = myLng;
        this.alertRead = alertRead;
    }
}
