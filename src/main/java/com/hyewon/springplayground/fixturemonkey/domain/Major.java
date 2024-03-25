package com.hyewon.springplayground.fixturemonkey.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Major {
    String name;
    String department;

    @Builder
    public Major(String name, String department) {
        this.name = name;
        this.department = department;
    }
}
