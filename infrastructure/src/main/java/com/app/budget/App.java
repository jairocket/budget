package com.app.budget;

import com.app.budget.configuration.WebServerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = "com.app.budget")
public class App {
    public static void main(String[] args) {

        System.out.println("Hello World!");
        SpringApplication.run(WebServerConfiguration.class, args);
    }
}
