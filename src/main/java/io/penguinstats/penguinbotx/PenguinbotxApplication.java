package io.penguinstats.penguinbotx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

/**
 * @author yamika
 */
@EnableCaching
@EnableAsync
@SpringBootApplication
public class PenguinbotxApplication {


    public static void main(String[] args) {

        SpringApplication.run(PenguinbotxApplication.class, args);

    }



}
