package com.example.CUSHProject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    sell("sell", "판매중"),
    soldOut("soldOut","판매완료");

    private final String value;
    private final String title;
}
