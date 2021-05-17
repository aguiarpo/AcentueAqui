package com.pac.acentueaqui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class AcentueAquiApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

    public static void main(String[] args) {
        SpringApplication.run(AcentueAquiApplication.class, args);
    }

}
