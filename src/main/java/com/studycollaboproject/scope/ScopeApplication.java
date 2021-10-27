package com.studycollaboproject.scope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class ScopeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScopeApplication.class, args);
    }


    @PostConstruct
    public void before() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}

