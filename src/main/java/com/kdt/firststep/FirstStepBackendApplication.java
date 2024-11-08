package com.kdt.firststep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Auditing 기능 활성화
public class FirstStepBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstStepBackendApplication.class, args);
    }

}
