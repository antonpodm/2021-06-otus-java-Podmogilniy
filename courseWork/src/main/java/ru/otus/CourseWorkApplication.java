package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class CourseWorkApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(CourseWorkApplication.class, args);

      //  var data = context.getBean("RestService", RestService.class).getRequestData("607");
       // System.out.println(data);
    }
}
