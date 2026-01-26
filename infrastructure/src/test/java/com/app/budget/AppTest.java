package com.app.budget;

import junit.framework.TestCase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;

/**
 * Unit test for simple App.
 */
@TestConfiguration(proxyBeanMethods = false)
@ActiveProfiles("test-containers")
public class AppTest
        extends TestCase {

    public static void main(String[] args) {
        SpringApplication.from(App::main).with(AppTest.class).run(args);
    }
}
