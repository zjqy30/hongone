package com.hone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.hone.dao")
@EnableScheduling
public class HongoneApplication     {
//public class HongoneApplication  extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(HongoneApplication.class, args);
	}
}
