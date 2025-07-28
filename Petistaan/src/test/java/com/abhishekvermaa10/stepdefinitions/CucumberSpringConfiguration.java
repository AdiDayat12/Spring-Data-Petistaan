package com.abhishekvermaa10.stepdefinitions;

import com.abhishekvermaa10.Demo;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.profiles.active=test")
public class CucumberSpringConfiguration {
    @Autowired
    public void printActiveProfiles(org.springframework.core.env.Environment environment) {
        System.out.println(">>> Active profile: " + Arrays.toString(environment.getActiveProfiles()));
    }
}