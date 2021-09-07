package com.example.CUSHProject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
public enum HairType {
    Cut("Cut","커트"),
    Perm("perm","파마"),
    Dyeing("Dyeing","염색"),
    Design("Design","디자인");

    private String value;
    private String title;

}
