package com.hyewon.springplayground.fixturemonkey.domain;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Validated
public class Student {

    private String id;
    private String name;
    private List<Major> majors;
    private int age;
    private boolean onLeave; // 휴학 여부
    private boolean graduated; // 졸업 여부

    @Builder
    public Student(
            final String id,
            final String name,
            final List<Major> majors,
            final int age,
            final boolean onLeave,
            final boolean graduated
    ) {
        this.id = id;
        this.name = name;
        this.majors = majors;
        this.age = age;
        this.onLeave = onLeave;
        this.graduated = graduated;
    }

    public boolean isLeave() {
        return onLeave;
    }

    public boolean isGraduated() {
        return graduated;
    }

    public boolean isDoubleMajor() {
        return majors.size() > 1;
    }
}
