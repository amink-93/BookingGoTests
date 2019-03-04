package com.bookinggo.test.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Runner {

    public static void main(String[] args) {

        ConfigurableApplicationContext tmp = SpringApplication.run(Runner.class, args);
        if (tmp.isActive()) {

            System.out.println("is active");
        } else {

            System.out.println("not active");
        }

    }

}
