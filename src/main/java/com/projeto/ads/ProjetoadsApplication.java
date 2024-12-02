package com.projeto.ads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="com.projeto.ads")
@ComponentScan(basePackages="com.projeto.ads")
@EntityScan(basePackages="com.projeto.ads")
@SpringBootApplication
public class ProjetoadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoadsApplication.class, args);
	}

}
