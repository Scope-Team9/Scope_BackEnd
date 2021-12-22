package com.studycollaboproject.scope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.TimeZone;

@EnableCaching
@EnableJpaAuditing
@EnableAspectJAutoProxy
@SpringBootApplication
public class ScopeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScopeApplication.class, args);
    }


    @PostConstruct
    public void before() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        File file = new File("./images");
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        file = new File("./logs");
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}

