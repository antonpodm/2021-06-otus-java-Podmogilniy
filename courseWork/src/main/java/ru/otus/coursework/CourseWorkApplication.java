package ru.otus.coursework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CourseWorkApplication {
    public static void main(String[] args) {
        var context = SpringApplication.run(CourseWorkApplication.class, args);
    }
}
