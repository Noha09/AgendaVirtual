package com.um.AgentaVirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication
public class AgentaVirtualApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgentaVirtualApplication.class, args);
	}

    @Bean
    SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }
}
