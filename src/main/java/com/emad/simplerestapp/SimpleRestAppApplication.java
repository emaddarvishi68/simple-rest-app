package com.emad.simplerestapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class SimpleRestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleRestAppApplication.class, args);
	}

}
