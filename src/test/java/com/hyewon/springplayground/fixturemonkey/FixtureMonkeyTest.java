package com.hyewon.springplayground.fixturemonkey;

import com.hyewon.springplayground.fixturemonkey.domain.Major;
import com.hyewon.springplayground.fixturemonkey.domain.Student;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FixtureMonkeyTest {

    @Test
    public void 기본테스트() {
        // given
        Student student = Student.builder()
                .id("1")
                .name("홍길동")
                .majors(List.of(Major.builder().name("컴퓨터공학").department("소프트웨어학과").build()))
                .age(20)
                .onLeave(false)
                .graduated(false)
                .build();

        // when
        boolean isLeave = student.isLeave();
        boolean isGraduated = student.isGraduated();
        boolean isDoubleMajor = student.isDoubleMajor();

        // then
        assertThat(isLeave).isFalse();
        assertThat(isGraduated).isFalse();
        assertThat(isDoubleMajor).isFalse();
    }

    @Test
    public void 픽스쳐몽키사용() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
                .build();

        // given
        Student student = fixtureMonkey.giveMeBuilder(Student.class)
                        .set("id", "1")
                        .set("name", "홍길동")
                        .set("majors", List.of(Major.builder().name("컴퓨터공학").department("소프트웨어학과").build()))
                        .set("age", 20)
                        .set("onLeave", false)
                        .set("graduated", false)
                        .sample();

        assertThat(student.isLeave()).isFalse();
        assertThat(student.isGraduated()).isFalse();
        assertThat(student.isDoubleMajor()).isFalse();
    }



    @Test
    public void BeanValidation_픽스쳐몽키사용() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();

        Student validatedStudent = fixtureMonkey.giveMeOne(Student.class);

        System.out.println(validatedStudent.toString());
        assertThat(validatedStudent.isLeave()).isFalse();
        assertThat(validatedStudent.isGraduated()).isFalse();
        assertThat(validatedStudent.isDoubleMajor()).isFalse();
    }
}
